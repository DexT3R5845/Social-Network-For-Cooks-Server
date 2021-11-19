package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@RequiredArgsConstructor
public class PasswordResetToken {
    private Long id;
    @NonNull
    private String resetToken;
    @NonNull
    private LocalDateTime expiryDate;
    @NonNull
    private long accountId;
    @NonNull
    private boolean active;

    public PasswordResetToken(){}
}