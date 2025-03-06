package com.example.securitymicroservice.services.email;

import com.example.securitymicroservice.models.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    @Override
    public void sendActivationEmail(String name, String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Activación de cuenta");
        message.setText("Hola " + name.toUpperCase()
                + ", para activar su cuenta, haga clic en el siguiente enlace: http://localhost:8080/api/v1/users/registration?token="
                + token);
        mailSender.send(message);
    }

    @Override
    public void sendRecoveryEmail(UserDTO userDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userDTO.getEmail());
        message.setSubject("Recuperación de contraseña");
        message.setText("Hola " + userDTO.getName().toUpperCase()
                + ", Para recuperar su contraseña, haga clic en el siguiente enlace: http://localhost:8080/usuarios/recuperar?token="
                + userDTO.getToken());
        mailSender.send(message);
    }

}
