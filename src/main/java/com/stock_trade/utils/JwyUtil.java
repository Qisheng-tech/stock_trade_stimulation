package com.stock_trade.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwyUtil {
    // 用于签名的密钥
    private static final String SECRET_KEY =
            "your-secret-key-your-secret-key-your-secret-key";

    // 创建密钥
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * 生成 JWT
     */
    public static String generateToken(Long userId, String username) {

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 JWT
     */
    public static Claims parseToken(String token) {

        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
