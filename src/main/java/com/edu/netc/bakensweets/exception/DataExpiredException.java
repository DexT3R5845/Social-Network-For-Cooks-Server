package com.edu.netc.bakensweets.exception;

public class DataExpiredException extends RuntimeException {
    public DataExpiredException(String message){
        super(message);
    }
}
