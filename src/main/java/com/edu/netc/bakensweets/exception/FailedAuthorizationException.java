package com.edu.netc.bakensweets.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class FailedAuthorizationException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;
    private final boolean need_captcha;
}
