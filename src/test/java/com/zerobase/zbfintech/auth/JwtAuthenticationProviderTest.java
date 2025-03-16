package com.zerobase.zbfintech.auth;

import com.zerobase.zbfintech.entity.UserVo;
import com.zerobase.zbfintech.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class JwtAuthenticationProviderTest {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private String userPk = "testUser";
    private Long userId = 123L;
    private UserType userType = UserType.USER;

    @Test
    @DisplayName("토큰 생성 확인")
    public void successCreateToken() {
        //given
        String token = jwtAuthenticationProvider.createToken(userPk, userId, userType);

        //when & then
        assertNotNull(token);
        assertTrue(token.length() > 0);

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        assertNotNull(claims);
        assertEquals(userPk, Aes256Util.decrypt(claims.getSubject()));  // 암호화된 값이므로 복호화 후 비교
        assertEquals(userId.toString(), Aes256Util.decrypt(claims.getId()));
    }

    @Test
    @DisplayName("토큰으로 유저 정보 가져오기")
    public void successGetUserVo() {
        // given 토큰 생성
        String token = jwtAuthenticationProvider.createToken(userPk, userId, userType);

        //when & then
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        assertNotNull(userVo);
        assertEquals(userId, userVo.getId());
        assertEquals(userPk, userVo.getEmail());
    }

    @Test
    @DisplayName("토큰 만료시간 확인")
    public void testValidateToken() {
        String token = jwtAuthenticationProvider.createToken(userPk, userId, userType);

        boolean isValid = jwtAuthenticationProvider.validateToken(token);
        assertTrue(isValid);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 60 * 60 * 1000);
        claims.setExpiration(expiredDate);

        String expiredToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        boolean isExpiredValid = jwtAuthenticationProvider.validateToken(expiredToken);
        assertFalse(isExpiredValid);
    }


}