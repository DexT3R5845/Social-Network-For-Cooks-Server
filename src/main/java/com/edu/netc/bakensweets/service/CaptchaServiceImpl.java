package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.payload.CaptchaResponse;
import com.edu.netc.bakensweets.service.interfaces.CaptchaService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${recaptcha.private.key}")
    private String recaptchaPrivateKey;

    public boolean isValidCaptcha(String recapResponse) {

        RestTemplate restTemplate = new RestTemplate();
        String url= "https://www.google.com/recaptcha/api/siteverify";
        String params= String.format("?secret=%s&response=%s", recaptchaPrivateKey, recapResponse);
        String completeUrl=url+params;
        CaptchaResponse resp= restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
        return resp.isSuccess();
    }
}
