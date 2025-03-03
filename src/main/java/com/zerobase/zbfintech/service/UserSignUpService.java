package com.zerobase.zbfintech.service;

import com.zerobase.zbfintech.dto.SignupForm;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.exception.CustomException;
import com.zerobase.zbfintech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.zerobase.zbfintech.exception.ErrorCode.*;

@Service
public class UserSignUpService {

    @Value("${email.verification.url-prefix}")
    private String verificationUrlPrefix;

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserSignUpService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Transactional
    public String userSignUp(SignupForm form) {
        if (isEmailExists(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        }
        User user = signup(form);
        String code = getRandomCode();
        emailService.sendEmail(form.getEmail(),
                getVerificationEmailBody(user.getEmail(), user.getUsername(), code));
        changeCustomerValidateEmail(user.getId(), code);
        return "회원 가입에 성공했습니다.";

    }

    public User signup(SignupForm form) {
        return userRepository.save(User.from(form));
    }

    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    private void changeCustomerValidateEmail(Long userId, String verificationCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ALREADY_REGISTER_USER));
        user.setVerificationCode(verificationCode);
        user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("안녕하세요 ").append(name).append("님 링크를 눌러 인증을 진행해주세요\n\n")
                .append(verificationUrlPrefix)
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        if(user.is_email_verified()) {
            throw new CustomException(ALREADY_VERIFY);
        } else if(!user.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        } else if(user.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }
        user.set_email_verified(true);
    }

}
