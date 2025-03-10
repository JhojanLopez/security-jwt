package com.example.securitymicroservice.services.user;

import com.example.securitymicroservice.database.entities.User;
import com.example.securitymicroservice.database.repositories.UserRepository;
import com.example.securitymicroservice.exceptions.UserNotFoundByEmail;
import com.example.securitymicroservice.models.PreSignUpDTO;
import com.example.securitymicroservice.models.SignUpDTO;
import com.example.securitymicroservice.models.UserDTO;
import com.example.securitymicroservice.services.email.EmailService;
import com.example.securitymicroservice.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<String> preSignUp(PreSignUpDTO preSignUpDTO) {
        Optional<User> userConsulted = userRepository.findByEmail(preSignUpDTO.getEmail());
        User userFound = userConsulted.orElse(null);
        if (userFound != null && !userFound.isActive()) {
            emailService.sendActivationEmail(userFound.getName(), userFound.getEmail(), userFound.getToken());
            return ResponseEntity.ok("El correo electrónico ya está registrado. " +
                    "Te enviaremos de nuevo el enlace a tu correo para continuar el registro.");
        } else if (userFound != null) {
            return ResponseEntity.ok("El correo electrónico ya está registrado." +
                    " Por favor inicia sesion.");
        }

        User user = mapper.map(preSignUpDTO, User.class);
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(PasswordUtils.generatePassword()));
        user.setToken(UUID.randomUUID().toString());//used to active and recover accounts

        User userSaved = userRepository.save(user);
        emailService.sendActivationEmail(userSaved.getName(), userSaved.getEmail(), userSaved.getToken());
        return ResponseEntity.ok("Pre registo con exito! a tu correo te llegara un link para finalizar el registro.");
    }

    @Override
    public UserDTO registerUser(SignUpDTO signUpDTO) {
        if (userRepository.existsUserByEmail(signUpDTO.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado. ");
        }

        UserDTO userDTO = mapper.map(signUpDTO, UserDTO.class);
        userDTO.setActive(false);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setToken(UUID.randomUUID().toString());//used to active and recover accounts

        User entity = mapper.map(userDTO, User.class);
        User userSaved = userRepository.save(entity);
        UserDTO userSavedDTO = mapper.map(userSaved, UserDTO.class);
        emailService.sendActivationEmail(userSavedDTO.getName(), userSavedDTO.getEmail(), userSavedDTO.getToken());
        return userSavedDTO;
    }

    @Override
    public void activateUser(String token, String password) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setToken(UUID.randomUUID().toString());//used to active and recover accounts

        userRepository.save(user);
    }

    @Override
    public void recoverPassword(String token, String password) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));
        user.setPassword(passwordEncoder.encode(password));
        user.setToken(UUID.randomUUID().toString());

        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundByEmail("Usuario no encontrado"));
    }

    @Override
    public UserDTO findUserDTOByEmail(String email) {
        User user = this.findUserByEmail(email);
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public SignUpDTO findUserByToken(String token) {
        Optional<User> userByToken = userRepository.findByToken(token);
        return userByToken.map(user -> mapper.map(user, SignUpDTO.class)).orElse(null);

    }

}
