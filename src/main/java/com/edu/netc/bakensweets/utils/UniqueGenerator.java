package com.edu.netc.bakensweets.utils;

import java.util.UUID;

public class UniqueGenerator {

    public static Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
    }
}
