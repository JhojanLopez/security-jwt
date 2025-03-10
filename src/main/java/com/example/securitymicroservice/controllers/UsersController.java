package com.example.securitymicroservice.controllers;

import com.example.securitymicroservice.models.SignUpDTO;
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
@RequestMapping("${spring.application.apis.users.base-path}")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping("${spring.application.apis.users.endpoints.registration}")
    public String registration(@RequestParam String token, Model model) {
        if (!this.validateToken(token)) {
            return "expired-token";
        }

        SignUpDTO userByToken = userService.findUserByToken(token);
        if (userByToken == null) {
            return "expired-token";
        }
        //Verify user
        if (!model.containsAttribute("user")) {
            userByToken.setRegistration(true);
            log.info("userByToken" + userByToken);
            model.addAttribute("error", false);
            model.addAttribute("user", userByToken);
        }
        return "sign-up";
    }

    @PostMapping("/assign-password")
    public String assignPassword(@ModelAttribute SignUpDTO user, Model model, RedirectAttributes redirectAttributes) {
        // Verify passwords
        if (!user.getNewPassword().equals(user.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("error", true);

            if (user.isRegistration()) {
                return "redirect:/api/v1/users/registration?token="+user.getToken();
            }

            return "redirect:/api/v1/users/recovery/password?token="+user.getToken();
        }
        //activating user
        if (user.isRegistration()) {
            model.addAttribute("registration", true);
            userService.activateUser(user.getToken(), user.getNewPassword());
        } else {//recovering password
            model.addAttribute("registration", false);
            userService.recoverPassword(user.getToken(), user.getNewPassword());
        }
        return "password-assigned";
    }

    @GetMapping("${spring.application.apis.users.endpoints.recovery-password}")
    public String recoveryPassword(@RequestParam String token, Model model) {
        if (!this.validateToken(token)) {
            return "expired-token";
        }

        SignUpDTO userByToken = userService.findUserByToken(token);
        if (userByToken == null) {
            return "expired-token";
        }
        //Verify user
        if (!model.containsAttribute("user")) {

            log.info("[recovery password] userByToken" + userByToken);
            model.addAttribute("error", false);
            model.addAttribute("user", userByToken);
        }
        return "password-recovery";
    }

    private boolean validateToken(String token) {
        return token != null && !token.isBlank();
    }

}
