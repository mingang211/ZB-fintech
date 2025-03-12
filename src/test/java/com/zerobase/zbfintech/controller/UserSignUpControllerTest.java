package com.zerobase.zbfintech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.zbfintech.dto.SignupForm;
import com.zerobase.zbfintech.entity.User;
import com.zerobase.zbfintech.repository.UserRepository;
import com.zerobase.zbfintech.service.UserSignUpService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserSignUpControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserSignUpService userSignUpService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserSignUpController userSignUpController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원 가입 요청 - 성공")
    void successfulSignUp() throws Exception {
        // given
        SignupForm form = SignupForm.builder()
                .email("a989977@naver.com")
                .password("passworD#")
                .username("username")
                .build();

        given(userSignUpService.userSignUp(any(SignupForm.class)))
                .willReturn("회원 가입에 성공했습니다.");

        // when & then
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("회원 가입에 성공했습니다."));
    }

    @Test
    @DisplayName("메일 인증 요청 - 성공")
    void successVerifyEmail() throws Exception {
        // given
        User user = User.builder()
                .email("a989977@naver.com")
                .password("passworD#")
                .username("username")
                .isEmailVerified(false)
                .verificationCode("HTuSwIxx02")
                .verifyExpiredAt(LocalDateTime.now().plusMinutes(10))
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when & then
        mockMvc.perform(get("/signup/verify/user?email=" + user.getEmail() + "&code=" + user.getVerificationCode()))
                .andExpect(status().isOk())
                .andExpect(content().string("인증이 완료되었습니다."));

        verify(userSignUpService, times(1)).verifyEmail(user.getEmail(), user.getVerificationCode());
    }

}
