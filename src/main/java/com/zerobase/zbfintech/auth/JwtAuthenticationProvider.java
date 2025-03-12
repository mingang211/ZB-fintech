package com.zerobase.zbfintech.auth;

import com.zerobase.zbfintech.entity.UserVo;
import com.zerobase.zbfintech.util.Aes256Util;
import io.jsonwebtoken.*;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;


public class JwtAuthenticationProvider {


    @Value("${jwt.secret-key}")
    private String secretKey;
    private long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String userPk, Long id, UserType userType) {

        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk)).setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidTime))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public boolean validateToken(String JwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(JwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e) {
            return false;
        }
    }

    public UserVo getUserVo(String token) {
        Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(c.getId()))), Aes256Util.decrypt(c.getSubject()));
    }


    public Optional<UserVo> getValidatedUserVo(String token) throws ServletException {
        if (!validateToken(token)) {
            throw new ServletException("Invalid Access");
        }
        return Optional.ofNullable(getUserVo(token));
    }
}
