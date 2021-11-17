package com.edu.netc.bakensweets.model.payload;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    @NonNull
    private String token;
}
