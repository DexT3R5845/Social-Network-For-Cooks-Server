package com.edu.netc.bakensweets.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class FailedAuthorizationException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
    private boolean need_captcha;

    public FailedAuthorizationException(HttpStatus httpStatus, String message, boolean need_captcha){
        this.message = message;
        this.httpStatus = httpStatus;
        this.need_captcha = need_captcha;
    }
}
