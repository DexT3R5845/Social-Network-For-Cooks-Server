package com.edu.netc.bakensweets.utils;

public interface EmailSender {
    void sendSimpleMessage(String to, String subject, String message);
}
