package com.example.securitymicroservice.controllers;

import com.example.securitymicroservice.auth.exceptions.UserNotActiveException;
import com.example.securitymicroservice.auth.services.auth.AuthService;
import com.example.securitymicroservice.models.PreSignUpDTO;
import com.example.securitymicroservice.models.SignInDTO;
import com.example.securitymicroservice.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1/rest/session")
@RequiredArgsConstructor
public class SessionController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/pre-sign-up")
    public ResponseEntity<?> preSignUp(PreSignUpDTO preSignUpDTO) {
        try {
            return userService.preSignUp(preSignUpDTO);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Hubo un error interno, por favor intenta despues.");
        }

    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(SignInDTO signInDTO) {
        try {
            return ResponseEntity.ok(authService.authenticUser(signInDTO));
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Credenciales erroneas o Usuario inexistente. ");
        }
        catch (UserNotActiveException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception exc) {
            log.error("Error: {}", exc.getMessage());
            return ResponseEntity.internalServerError().body("Hubo un error interno, por favor intenta despues.");
        }

    }

}
