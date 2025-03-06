package com.example.securitymicroservice.auth.services.jwt;

public interface JwtService {
    String createToken(String email);
    boolean validateToken(String token);
}
