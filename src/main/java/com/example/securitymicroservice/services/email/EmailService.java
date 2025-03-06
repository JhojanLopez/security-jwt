package com.example.securitymicroservice.services.email;

import com.example.securitymicroservice.models.UserDTO;

public interface EmailService {
    void sendActivationEmail(String name, String email, String token);
    void sendRecoveryEmail(UserDTO userDTO);

}
