package com.zerobase.zbfintech.config;

public class JwtAuthenticationProvider {

    private String secretKey = "ZB-fintech-secret-key";
    private long tokenValidTime = 1000L * 60 * 60 * 24;

    private String createToken(String userPk, Long id, UserType userType) {
        return "";
    }
}
