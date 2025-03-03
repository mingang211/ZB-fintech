package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.dto.SignupForm;
import com.zerobase.zbfintech.service.UserSignUpService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    public UserSignUpController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;

    }
    @Operation(summary = "회원 가입", description = "회원 가입 신청을 보내고 인증 메일을 보냅니다")
    @PostMapping
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupForm form) {
        return ResponseEntity.ok(userSignUpService.userSignUp(form));
    }

    @Operation(summary = "회원 가입", description = "인증 메일을 받고 인증 확인")
    @GetMapping("/verify/user")
    public ResponseEntity<String> verifyUser(String email, String code){
        userSignUpService.verifyEmail(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}
