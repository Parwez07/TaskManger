package com.example.TaskManager.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static SecretKey secretKey;

    public JwtService(){
        secretKey = generateKey();
    }
    public String generateToken(String email) {

        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .and()
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getKey() {
        return secretKey;
    }

    private SecretKey generateKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            secretKey = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return secretKey;
    }

    public String extractEmail(String jwtToken) {

        Claims claims = extractClaims(jwtToken);
        return claims.getSubject();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidateToken(String jwtToken, UserDetails userDetails) {
        final String userEmail = extractEmail(jwtToken);
        return userEmail.equals(userDetails.getUsername())&& !isExpired(jwtToken);
    }

    public boolean isExpired(String token){
        return ExpirationTime(token).before(new Date(System.currentTimeMillis()));
    }
    public Date ExpirationTime(String token){
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }
}
