package com.vaccine.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - Child Vaccine Tracker");
        return "index";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login - Child Vaccine Tracker");
        return "login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register - Child Vaccine Tracker");
        model.addAttribute("registerRequest", new com.vaccine.tracker.dto.RegisterRequest());
        return "register";
    }
}