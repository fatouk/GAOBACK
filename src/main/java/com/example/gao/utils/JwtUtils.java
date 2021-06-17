/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gao.utils;

/**
 *
 * @author fakaloga
 */
import com.example.gao.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private JwtUtils() {}
    public static String generateToken(String principale, Map<String, Object> extra) {
        return Jwts.builder()
                .setSubject(principale)
                .setClaims(extra)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes())).compact();
    }
    public static String generateToken(String principale) {
        return generateToken(principale, new HashMap<>());
    }
}
