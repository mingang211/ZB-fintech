package com.zerobase.zbfintech.auth;

import com.zerobase.zbfintech.entity.UserVo;
import com.zerobase.zbfintech.util.Aes256Util;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Objects;


public class JwtAuthenticationProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-valid-time}")
    private long tokenValidTime;

    public String createToken(String userPk, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk))
                .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();
            Jws<Claims> claimsJws = jwtParserBuilder.setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public UserVo getUserVo(String token) {
        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();
        Claims c = jwtParserBuilder.setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(c.getId()))),
                Aes256Util.decrypt(c.getSubject()));
    }
}
