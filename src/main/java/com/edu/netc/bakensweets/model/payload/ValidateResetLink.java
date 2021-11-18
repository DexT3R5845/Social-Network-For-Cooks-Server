package com.edu.netc.bakensweets.model.payload;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ValidateResetLink {
    @NonNull
    private boolean expiry;
}
