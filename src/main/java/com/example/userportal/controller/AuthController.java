package com.example.userportal.controller;

import com.example.userportal.dto.ChangePasswordDto;
import com.example.userportal.dto.UserDto;
import com.example.userportal.entity.User;
import com.example.userportal.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model, Authentication authentication, @AuthenticationPrincipal User userDetails) {
        return "index";
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Passwords do not match");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/profile")
    public String editUser(Authentication authentication, Model model) {
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", userService.convertEntityToDto(user));
        return "profile";
    }
    @GetMapping("/users")
    public String findAllUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
       return "users-list";
    }
    @GetMapping("/change-password")
    public String loadChangePasswordPage(Model model) {
        model.addAttribute("changePassword", new ChangePasswordDto());
        return "change-password";
    }
    @PostMapping("/change-password")
    public String changePassword(Model model, @Valid @ModelAttribute("changePassword") ChangePasswordDto changePasswordDto,
                                 BindingResult result, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        if (!userService.passwordsMatch(changePasswordDto.getOldPassword(), user.getPassword())) {
            result.rejectValue("oldPassword", null, "Old password is incorrect");
        } else if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Passwords do not match");
        }
        if (result.hasErrors()) {
            model.addAttribute("changePassword", changePasswordDto);
            return "change-password";
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userService.update(user);
        return "redirect:/change-password?success";
    }
}
