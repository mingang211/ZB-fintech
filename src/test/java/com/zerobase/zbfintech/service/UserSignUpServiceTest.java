package com.zerobase.zbfintech.service;

import com.zerobase.zbfintech.dto.SignupForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserSignUpServiceTest {

    @Autowired
    private UserSignUpService userSignUpService;

    @Test
    void SignupTest() {
        //given
        SignupForm form = SignupForm.builder()
                .password("password")
                .email("email@email.com")
                .username("username")
                .build();
        //when
        //then
        assertNotNull(userSignUpService.signup(form));
    }
}