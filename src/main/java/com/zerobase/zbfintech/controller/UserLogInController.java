package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.dto.LoginForm;
import com.zerobase.zbfintech.service.UserLogInService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "로그인", description = "로그인 요청을 보내고 tokem을 생성합니다")
    @PostMapping("/user")
    public ResponseEntity<String> userLogIn(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(userLogInService.userLoginToken(loginForm.getEmail(),loginForm.getPassword()));
    }
}
