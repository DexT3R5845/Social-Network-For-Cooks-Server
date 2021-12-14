package com.edu.netc.bakensweets.model.payload;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class FailedAuthorization {
    private HttpStatus httpStatus;
    private String message;
    private boolean need_captcha;

    public FailedAuthorization(HttpStatus httpStatus, String message, boolean need_captcha){
        this.message = message;
        this.httpStatus = httpStatus;
        this.need_captcha = need_captcha;
    }
}
