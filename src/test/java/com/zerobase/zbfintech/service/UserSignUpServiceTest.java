package com.zerobase.zbfintech.service;

import com.zerobase.zbfintech.dto.SignupForm;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.exception.CustomException;
import com.zerobase.zbfintech.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.zerobase.zbfintech.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserSignUpServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserSignUpService userSignUpService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("a989977@naver.com")
                .username("username")
                .is_email_verified(false)
                .build();
    }

    @Test
    void testUserSignUpSuccess() {
        // given
        SignupForm form = SignupForm.builder()
                .email("test@naver.com")
                .username("username")
                .password("password")
                .build();
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        String result = userSignUpService.userSignUp(form);

        // then
        assertEquals("회원 가입에 성공했습니다.", result);
        verify(emailService, times(1)).sendEmail(anyString(), anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testUserSignUpEmailExists() {
        // given
        SignupForm form = new SignupForm("a989977@naver.com", "password", "username");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            userSignUpService.userSignUp(form);
        });
        assertEquals(ALREADY_REGISTER_USER.getDescription(), thrown.getMessage());
        assertEquals(ALREADY_REGISTER_USER, thrown.errorCode);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testVerifyEmailSuccess() {
        // given
        String email = "a989977@naver.com";
        String code = "ABC123456";
        user.setVerificationCode(code);
        user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        userSignUpService.verifyEmail(email, code);

        // then
        assertTrue(user.is_email_verified());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testVerifyEmailAlreadyVerified() {
        // given
        String email = "a989977@naver.com";
        String code = "ABC123456";
        user.set_email_verified(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            userSignUpService.verifyEmail(email, code);
        });
        assertEquals(ALREADY_VERIFY.getDescription(), thrown.getMessage());
        assertEquals(ALREADY_VERIFY, thrown.errorCode);
    }

    @Test
    void testVerifyEmailWrongCode() {
        // given
        String email = "a989977@naver.com";
        String code = "WRONGCODE";
        user.setVerificationCode("ABC123456");
        user.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            userSignUpService.verifyEmail(email, code);
        });
        assertEquals(WRONG_VERIFICATION.getDescription(), thrown.getMessage());
        assertEquals(WRONG_VERIFICATION, thrown.errorCode);
    }

    @Test
    void testVerifyEmailExpiredCode() {
        // given
        String email = "a989977@naver.com";
        String code = "ABC123456";
        user.setVerificationCode(code);
        user.setVerifyExpiredAt(LocalDateTime.now().minusDays(1));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            userSignUpService.verifyEmail(email, code);
        });
        assertEquals(EXPIRE_CODE.getDescription(), thrown.getMessage());
        assertEquals(EXPIRE_CODE, thrown.errorCode);
    }
}