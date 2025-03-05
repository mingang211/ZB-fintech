package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.dto.LoginForm;
import com.zerobase.zbfintech.service.UserLogInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logIn")
public class UserLogInController {

    private final UserLogInService userLogInService;

    public UserLogInController(UserLogInService userLogInService) {
        this.userLogInService = userLogInService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> userLogIn(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(userLogInService.userLoginToken(loginForm.getEmail(),loginForm.getPassword()));
    }
}
