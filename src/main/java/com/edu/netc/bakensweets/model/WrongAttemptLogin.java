package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class WrongAttemptLogin {
    private Long id;
    private String ip;
    private LocalDateTime expiryTime;
    private Integer countWrongAttempts;

    public WrongAttemptLogin(String ip, LocalDateTime expiryTime, Integer countWrongAttempts){
        this.ip = ip;
        this.expiryTime = expiryTime;
        this.countWrongAttempts = countWrongAttempts;
    }
}
