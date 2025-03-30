package com.vaccine.tracker.controller;

import com.vaccine.tracker.constant.MessageConstants;
import com.vaccine.tracker.dto.request.LoginRequest;
import com.vaccine.tracker.dto.request.RegisterRequest;
import com.vaccine.tracker.dto.response.JwtResponse;
import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.security.JwtTokenProvider;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for handling authentication operations.
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    /**
     * Display the login page.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    /**
     * Display the registration page.
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    /**
     * Process user login and generate JWT token.
     *
     * @param loginRequest login credentials
     * @return JWT response with token and user details
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Update last login time
        userService.updateLastLogin(loginRequest.getUsername());

        // Get user details
        User user = userService.findByUsername(loginRequest.getUsername());

        // Return JWT response
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        ));
    }

    /**
     * Process user registration.
     *
     * @param registerRequest registration details
     * @return success message or error
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Check if username exists
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageConstants.ERROR_USER_EXISTS, false));
        }

        // Check if email exists
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageConstants.ERROR_EMAIL_EXISTS, false));
        }

        // Register new user
        userService.register(registerRequest);

        return ResponseEntity.ok(new MessageResponse(MessageConstants.SUCCESS_REGISTER));
    }

    /**
     * Process user logout.
     *
     * @return success message
     */
    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout() {
        // In a stateless JWT authentication system, the server doesn't track sessions
        // The client is responsible for removing the JWT token
        // We just send a success response
        return ResponseEntity.ok(new MessageResponse(MessageConstants.SUCCESS_LOGOUT));
    }
}
