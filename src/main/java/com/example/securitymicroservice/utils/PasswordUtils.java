package com.example.securitymicroservice.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
@UtilityClass
public class PasswordUtils {
    private static final String CARACTERES_PERMITIDOS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CARACTERES_PERMITIDOS.length());
            password.append(CARACTERES_PERMITIDOS.charAt(index));
        }

        return password.toString();
    }
}
