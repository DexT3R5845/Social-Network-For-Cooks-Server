package com.edu.netc.bakensweets.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Error> handleException(AccessDeniedException e) {
    Error error = new Error(HttpStatus.FORBIDDEN, "Access Denied");
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Error> handleException(CustomException e) {
    Error error = new Error(e.getHttpStatus(), e.getMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponse handleValidationExceptions(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
              if (errors.containsKey(error.getField())) {
                errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
              } else {
                errors.put(error.getField(), error.getDefaultMessage());
              }
            }
    );
    return new ApiResponse(errors, "VALIDATION_FAILED");
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestParamException.class)
  public ApiResponse handleValidationExceptions(BadRequestParamException ex) {

    Map<String, String> errors = new HashMap<>();
    errors.put(ex.getParam(), ex.getParamMessage());
    return new ApiResponse(errors, ex.getMessage());
  }
}
