package com.zerobase.zbfintech.controller;

import com.zerobase.zbfintech.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEmailController {

    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-test-email")
    public String sendTestEmail() {
        emailService.sendEmail("a989977@naver.com", "Test Subject", "This is a test email.");
        return "Email sent!";
    }
}
