package com.example.securitymicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String name;
    private String email;
    private String token;
    private String newPassword;
    private String confirmPassword;
}
