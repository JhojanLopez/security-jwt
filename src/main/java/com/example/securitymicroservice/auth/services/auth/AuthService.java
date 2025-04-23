package com.example.securitymicroservice.auth.services.auth;

import com.example.securitymicroservice.models.SignInDTO;

public interface AuthService {
    String authenticUser(SignInDTO SignInDTO);
}
