package com.hzy.common.jwt;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @title: JwtHelper jwt工具类
 * @Author zxwyhzy
 * @Date: 2023/6/15 18:35
 * @Version 1.0
 */
public class JwtHelper {

    // 有效时间
    private static long tokenExpiration = 30 * 60 * 1000;
    // 加密密钥
    private static String tokenSignKey = "zxwyhzy";

    /**
     *  生成token字符串
     * @param userId 用户id
     * @param username 用户名称
     * @return token 字符串
     */
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                // 分类
                .setSubject("AUTH-USER")
                // 设置token有效时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                // 设置主体部分
                .claim("userId", userId)
                .claim("username", username)
                // 签名部分
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "admin");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}