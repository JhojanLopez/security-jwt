package com.example.securitymicroservice.services.user;

import com.example.securitymicroservice.models.PreSignUpDTO;
import com.example.securitymicroservice.models.SignUpDTO;
import com.example.securitymicroservice.models.UserDTO;
import com.example.securitymicroservice.database.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> preSignUp(PreSignUpDTO preSignUpDTO);
    UserDTO registerUser(SignUpDTO SignUpDTO);
    void activateUser(String token, String password);
    UserDTO recoverPassword(String email);
    User findUserByEmail(String email);
    SignUpDTO findUserByToken(String token);
}
