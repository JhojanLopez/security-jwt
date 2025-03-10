package com.example.securitymicroservice.exceptions;

public class UserNotFoundByEmail  extends RuntimeException {
    public UserNotFoundByEmail(String message) {
        super(message);
    }
}
