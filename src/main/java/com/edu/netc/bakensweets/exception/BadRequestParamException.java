package com.edu.netc.bakensweets.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
public class BadRequestParamException extends RuntimeException {
    private final String param;
    private final String paramMessage;
}
