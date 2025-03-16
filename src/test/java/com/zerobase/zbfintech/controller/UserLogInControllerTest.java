package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.dto.LoginForm;
import com.zerobase.zbfintech.service.UserLogInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class UserLogInControllerTest {

    @Mock
    private UserLogInService userLogInService;

    @InjectMocks
    private UserLogInController userLogInController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Set up the MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(userLogInController).build();
    }

    @Test
    @DisplayName("로그인 - 성공")
    void userLogIn_success() throws Exception {
        // Arrange
        LoginForm loginForm = new LoginForm("test@example.com", "password");
        String expectedToken = "mockToken";
        when(userLogInService.userLoginToken("test@example.com", "password")).thenReturn(expectedToken);

        // Act & Assert
        mockMvc.perform(post("/logIn/user")
                        .contentType("application/json")
                        .content("{\"email\": \"test@example.com\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedToken));
    }

}