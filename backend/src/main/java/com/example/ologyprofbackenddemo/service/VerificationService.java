package com.example.ologyprofbackenddemo.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface VerificationService {
    Void SendEmail(JavaMailSender javaMailSender, String email);
    Void VerifyCode(String email, String code);
}
