package com.zerobase.zbfintech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm {
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotNull
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "비밀번호는 대소문자, 특수문자를 포함하여야 합니다.")
    private String password;
    @NotNull(message = "이름은 필수 입력값입니다.")
    private String username;

}
