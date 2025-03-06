package com.example.securitymicroservice.auth.services.auth;

import com.example.securitymicroservice.models.SignInDTO;
import com.example.securitymicroservice.models.UserDTO;

public interface AuthService {
    String authenticUser(SignInDTO SignInDTO);
}
