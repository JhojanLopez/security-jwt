package com.example.securitymicroservice.auth.services.auth;

import com.example.securitymicroservice.auth.exceptions.UserNotActiveException;
import com.example.securitymicroservice.auth.services.jwt.JwtService;
import com.example.securitymicroservice.database.entities.User;
import com.example.securitymicroservice.models.SignInDTO;
import com.example.securitymicroservice.services.email.EmailService;
import com.example.securitymicroservice.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailService emailService;

    @Override
    public String authenticUser(SignInDTO signInDTO) {
        //authentication
        Authentication authentication =
                authenticationManager.authenticate
                        (new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));

        User userByEmail = userService.findUserByEmail(signInDTO.getEmail());
        if (userByEmail != null && !userByEmail.isActive()) {
            emailService.sendActivationEmail(userByEmail.getName(), userByEmail.getEmail(), userByEmail.getToken());
            throw new UserNotActiveException("Usuario no activo. Te enviaremos de nuevo el enlace a tu correo para continuar el registro. ");

        }

        String name = authentication.getName();
        return jwtService.createToken(name);
    }

}
