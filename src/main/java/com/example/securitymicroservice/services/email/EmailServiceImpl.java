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

    @Value("${spring.application.host}")
    private String host;
    @Value("${spring.application.apis.users.base-path}")
    private String usersBasePath;
    @Value("${spring.application.apis.users.endpoints.registration}")
    private String registrationEndpoint;
    @Value("${spring.application.apis.users.endpoints.recovery-password}")
    private String recoveryPasswordEndpoint;
    @Value("${spring.application.client.active}")
    private boolean clientActive;
    @Value("${spring.application.client.host}")
    private String clientHost;
    @Value("${spring.application.client.endpoints.registration}")
    private String clientRegistrationEndpoint;
    @Value("${spring.application.client.endpoints.recovery-password}")
    private String clientRecoveryPasswordEndpoint;
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    @Override
    public void sendActivationEmail(String name, String email, String token) {
        String activationUrl = clientActive ? clientHost + clientRegistrationEndpoint + "?token=" + token
                : host + usersBasePath + registrationEndpoint + "?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Activación de cuenta");
        message.setText("Hola " + name.toUpperCase()
                + ", para activar su cuenta, haga clic en el siguiente enlace: " + activationUrl);
        mailSender.send(message);
    }

    @Override
    public void sendPasswordRecoveryEmail(UserDTO userDTO) {
        String recoveryPasswordUrl = clientActive ? clientHost + clientRecoveryPasswordEndpoint + "?token=" + userDTO.getToken()
                : host + usersBasePath + recoveryPasswordEndpoint + "?token=" + userDTO.getToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(userDTO.getEmail());
        message.setSubject("Recuperación de contraseña");
        message.setText("Hola " + userDTO.getName().toUpperCase()
                + ", Para recuperar su contraseña, haga clic en el siguiente enlace: " + recoveryPasswordUrl);
        mailSender.send(message);
    }

}
