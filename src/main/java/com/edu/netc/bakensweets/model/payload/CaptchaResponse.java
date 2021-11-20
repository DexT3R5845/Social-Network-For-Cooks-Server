package com.edu.netc.bakensweets.model.payload;

import lombok.Data;

@Data
public class CaptchaResponse{

        private boolean success;
        private String challenge_ts;
        private String hostname;
}
