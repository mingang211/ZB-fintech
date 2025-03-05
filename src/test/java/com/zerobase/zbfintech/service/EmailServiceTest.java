package com.zerobase.zbfintech.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail() {
        // given
        String to = "recipient@example.com";
        String text = "Test Email Body";

        // mock mailSender.send() 호출
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        emailService.sendEmail(to, text);

        // then
        // mailSender.send()가 호출되었는지 확인
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}