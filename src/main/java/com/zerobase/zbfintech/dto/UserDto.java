package com.zerobase.zbfintech.dto;

import com.zerobase.zbfintech.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getEmail());
    }
}
