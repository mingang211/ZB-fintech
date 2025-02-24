package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.dto.SignupForm;
import com.zerobase.zbfintech.service.UserSignUpService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/signup")
public class UserController {

    private final UserSignUpService userSignUpService;

    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;

    }
    @Operation(summary = "회원 가입", description = "회원 가입 신청을 보내고 인증 메일을 보냅니다")
    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody SignupForm form) {
        return ResponseEntity.ok(userSignUpService.userSignUp(form));
    }
}
