package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.auth.JwtAuthenticationProvider;
import com.zerobase.zbfintech.dto.UserDto;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.entity.UserVo;
import com.zerobase.zbfintech.exception.CustomException;
import com.zerobase.zbfintech.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.zbfintech.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtAuthenticationProvider provider;
    private final UserService userService;

    public UserController(JwtAuthenticationProvider provider, UserService userService) {
        this.provider = provider;
        this.userService = userService;
    }

    @Operation(summary = "회원 정보 가져오기", description = "로그인한 회원의 정보를 가져옵니다.")
    @GetMapping("/getInfo")
    public ResponseEntity<UserDto> getInfo(@RequestHeader(name="Authorization") String token){
        UserVo vo = provider.getUserVo(token);
        User u = userService.findByIdAndEmail(vo.getId(),vo.getEmail()).orElseThrow(
                ()-> new CustomException(NOT_FOUND_USER)
        );
        return ResponseEntity.ok(UserDto.from(u));
    }
}
