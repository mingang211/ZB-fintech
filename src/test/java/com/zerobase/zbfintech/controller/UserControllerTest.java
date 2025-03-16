package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.auth.JwtAuthenticationProvider;
import com.zerobase.zbfintech.auth.UserType;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.entity.UserVo;
import com.zerobase.zbfintech.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private JwtAuthenticationProvider provider;

    @Test
    @DisplayName("회원 정보 불러오기")
    void successGetInfo() throws Exception {

        //given
        UserVo mockUserVo = new UserVo(1L, "test@example.com");
        User mockUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .username("test")
                .password("Password@")
                .build();
        String token = provider.createToken(mockUser.getEmail(),mockUserVo.getId(), UserType.USER);
        System.out.println(token);
        //when
        when(userService.findByIdAndEmail(mockUserVo.getId(), mockUserVo.getEmail()))
                .thenReturn(Optional.of(mockUser));
        //then
        mockMvc.perform(get("/user/getInfo")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

}