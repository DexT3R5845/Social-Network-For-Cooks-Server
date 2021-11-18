package com.edu.netc.bakensweets.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
public class PasswordResetToken {
    private Long id;
    private String resetToken;
    private OffsetDateTime expiryDate;
    private long accountId;
    private boolean active;
}
