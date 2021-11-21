package com.edu.netc.bakensweets.service;

public interface EmailSenderService {
    void sendSimpleMessage(String to, String subject, String message);
    void sendResetLinkPassword(String to, String token);
}
