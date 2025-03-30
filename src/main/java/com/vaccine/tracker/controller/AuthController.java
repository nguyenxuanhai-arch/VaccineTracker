package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.RegisterRequest;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    private final UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest, 
                              BindingResult result, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        
        // Validate form
        if (result.hasErrors()) {
            model.addAttribute("error", "Please correct the form errors");
            model.addAttribute("title", "Register - Child Vaccine Tracker");
            return "register";
        }
        
        // Check if passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("title", "Register - Child Vaccine Tracker");
            return "register";
        }
        
        // Check if username exists
        if (userService.existsByUsername(registerRequest.getUsername())) {
            model.addAttribute("error", "Username is already taken");
            model.addAttribute("title", "Register - Child Vaccine Tracker");
            return "register";
        }
        
        // Check if email exists
        if (userService.existsByEmail(registerRequest.getEmail())) {
            model.addAttribute("error", "Email is already in use");
            model.addAttribute("title", "Register - Child Vaccine Tracker");
            return "register";
        }
        
        // Create new user
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword()); // Will be encoded in the service
        newUser.setFullName(registerRequest.getFullName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setRole(registerRequest.getRole());
        
        userService.save(newUser);
        
        // Success message
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }
}