package com.order_management.Order_management_system.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";
    private static final Key secret_key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long experiatonTimeMilliseconds = 1000*60*60;
    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + experiatonTimeMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    public String extractUserName(String token)
    {
       return Jwts.parser().setSigningKey(secret_key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidToken(String token, UserDetails userDetails, String username)
    {
        boolean isValidName = userDetails.getUsername().equals(username);
        boolean isValidToken = Jwts.parser().setSigningKey(secret_key).build().parseClaimsJws(token)
                .getBody().getExpiration().after(new Date());
        return isValidName && isValidToken;
    }

}
