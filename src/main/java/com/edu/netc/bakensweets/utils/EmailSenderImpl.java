package com.edu.netc.bakensweets.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender{
private final JavaMailSender emailSender;

public EmailSenderImpl(JavaMailSender emailSender){
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
}
