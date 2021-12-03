package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.service.interfaces.EmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender emailSender;
    @Value("${currentlyDomainClient}")
    private String currentlyDomainClient;

    public EmailSenderServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendResetLinkPassword(String to, String token) {
        sendSimpleMessage(to, "Reset Link Password", String.format("Reset Link Password: %s/account/reset-password/%s", currentlyDomainClient, token));
    }

    @Override
    public void sendNewModerLinkPassword(String to, String token) {
        sendSimpleMessage(to, "Password creation", String.format("Password creation link: %s%s", currentlyDomainClient, token));
    }
}
