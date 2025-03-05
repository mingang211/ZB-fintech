package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.dto.LoginForm;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.exception.CustomException;
import com.zerobase.zbfintech.service.UserLogInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.zbfintech.exception.ErrorCode.LOGIN_CHECK_FAIL;

@RestController
@RequestMapping("/logIn")
public class UserLogInController {

    private final UserLogInService userLogInService;

    public UserLogInController(UserLogInService userLogInService) {
        this.userLogInService = userLogInService;
    }

    @PostMapping("/user")
    public String userLogIn(@RequestBody LoginForm loginForm) {
        User loginuser = userLogInService.findValidUser(loginForm.getEmail(), loginForm.getPassword())
                .orElseThrow(()-> new CustomException(LOGIN_CHECK_FAIL));
        return "success";
    }
}
