package com.example.securitymicroservice.controllers;

import com.example.securitymicroservice.database.entities.User;
import com.example.securitymicroservice.models.SignUpDTO;
import com.example.securitymicroservice.models.UserDTO;
import com.example.securitymicroservice.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Log4j2
@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(@RequestParam String token, Model model) {
        if (token == null || token.isBlank()) {
            return "expired-token";
        }

        SignUpDTO userByToken = userService.findUserByToken(token);
        if (userByToken == null) {
            return "expired-token";
        }
        //Verify user
        if (!model.containsAttribute("user")) {

            log.info("userByToken" + userByToken);
            model.addAttribute("error", false);
            model.addAttribute("user", userByToken);
        }
        return "sign-up";
    }

    @PostMapping("/assign-password")
    public String assignPassword(@ModelAttribute SignUpDTO user, RedirectAttributes redirectAttributes) {
        // Verify passwords
        if (!user.getNewPassword().equals(user.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/api/v1/users/registration?token="+user.getToken();
        }
        //activating user
        userService.activateUser(user.getToken(), user.getNewPassword());

        return "password-assigned"; // Redirigir a una página de éxito
    }
}
