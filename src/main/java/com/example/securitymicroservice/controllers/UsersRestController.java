package com.example.securitymicroservice.controllers;

import com.example.securitymicroservice.exceptions.UserNotFoundByEmail;
import com.example.securitymicroservice.models.UserDTO;
import com.example.securitymicroservice.services.email.EmailService;
import com.example.securitymicroservice.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Log4j2
@RestController
@RequestMapping("/api/v1/rest/users")
@RequiredArgsConstructor
public class UsersRestController {
    private final UserService userService;
    private final EmailService emailService;
    @PostMapping("/notification/recovery/password")
    public ResponseEntity<String> notificationRecoverPassword(String email) {
        try {
            UserDTO userDTO = userService.findUserDTOByEmail(email);
            if (!userDTO.isActive()) {
                emailService.sendActivationEmail(userDTO.getName(), userDTO.getEmail(), userDTO.getToken());
                return ResponseEntity.ok("Usuario no activo. Te enviaremos de nuevo el enlace a tu correo para continuar el registro. ");
            }

            emailService.sendPasswordRecoveryEmail(userDTO);
            return ResponseEntity.ok("Se enviara a tu correo electronico, un link para restaurar la contraseña.");
        }
        catch (UserNotFoundByEmail ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(ex.getMessage());
        }
        catch (Exception exc) {
            log.error("Error: {}", exc.getMessage());
            return ResponseEntity.internalServerError().body("Hubo un error interno, por favor intenta despues.");
        }
    }
}
