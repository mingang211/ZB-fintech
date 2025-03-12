package com.zerobase.zbfintech.service;

import com.zerobase.zbfintech.auth.JwtAuthenticationProvider;
import com.zerobase.zbfintech.auth.UserType;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.exception.CustomException;
import com.zerobase.zbfintech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zerobase.zbfintech.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
public class UserLogInService {

    private final UserRepository userRepository;
    private final JwtAuthenticationProvider provider;

    public UserLogInService(UserRepository userRepository, JwtAuthenticationProvider provider) {
        this.userRepository = userRepository;
        this.provider = provider;
    }

    public String userLoginToken(String email, String password) {
        User loginUser = findValidUser(email, password)
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        return provider.createToken(loginUser.getEmail(),loginUser.getId(), UserType.USER);
    }

    public Optional<User> findValidUser(String email, String password) {
        return userRepository.findByEmail(email).stream()
                .filter(user -> user.getPassword().equals(password)
                && user.isEmailVerified())
                .findFirst();
    }


}
