package com.edu.netc.bakensweets.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Error> handleException(AccessDeniedException e) {
    Error error = new Error(HttpStatus.FORBIDDEN, "Access Denied");
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<Error> handleException(JwtException e) {
    Error error = new Error(HttpStatus.GONE, e.getLocalizedMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Error> handleException(CustomException e) {
    Error error = new Error(e.getHttpStatus(), e.getMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Error> handleException(MethodArgumentNotValidException e) {
    Error error = new Error(HttpStatus.BAD_REQUEST, e.getMessage());
    return new ResponseEntity<>(error, error.getHttpStatus());
  }
}
