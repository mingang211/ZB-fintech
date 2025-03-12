package com.zerobase.zbfintech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Aes256Config {

    @Value("${aes256.key}")
    private String key;

    @Value("${aes256.iv}")
    private String iv;

    public String getKey() {
        return key;
    }

    public String getIv() {
        if (iv.length() < 16) {
            iv = String.format("%-16s", iv).replace(' ', '0');
        } else if (iv.length() > 16) {
            iv = iv.substring(0, 16);
        }
        return iv;
    }
}