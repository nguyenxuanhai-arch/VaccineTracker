package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.request.RegisterRequest;
import com.vaccine.tracker.dto.response.UserResponse;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.UserMapper;
import com.vaccine.tracker.repository.UserRepository;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional
    public User register(RegisterRequest registerRequest) {
        // Check if username exists
        if (existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        
        // Check if email exists
        if (existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        
        // Create new user
        User user = userMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_CUSTOMER); // Default role for new users
        
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public User update(Long id, UserResponse userResponse) {
        User user = findById(id);
        
        // Check if current user is authorized to update this user
        User currentUser = getCurrentUser();
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN) && !currentUser.getId().equals(id)) {
            throw new UnauthorizedException("You are not authorized to update this user");
        }
        
        userMapper.updateUserFromResponse(user, userResponse);
        
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        // Check if user exists
        User user = findById(id);
        
        // Check if current user is authorized to delete this user
        User currentUser = getCurrentUser();
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN) && !currentUser.getId().equals(id)) {
            throw new UnauthorizedException("You are not authorized to delete this user");
        }
        
        userRepository.delete(user);
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    @Override
    public List<User> findByFullNameContaining(String keyword) {
        return userRepository.findByFullNameContainingIgnoreCase(keyword);
    }
    
    @Override
    @Transactional
    public void updateLastLogin(String username) {
        User user = findByUsername(username);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public User updateEnabledStatus(Long id, boolean enabled) {
        User user = findById(id);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public User changeRole(Long id, Role role) {
        // Only admin can change roles
        if (!getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorizedException("Only administrators can change user roles");
        }
        
        User user = findById(id);
        user.setRole(role);
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public boolean changePassword(Long id, String currentPassword, String newPassword) {
        User user = findById(id);
        
        // Check if current user is authorized to change this user's password
        User currentUser = getCurrentUser();
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN) && !currentUser.getId().equals(id)) {
            throw new UnauthorizedException("You are not authorized to change this user's password");
        }
        
        // Verify current password (skip if admin is changing another user's password)
        if (currentUser.getId().equals(id)) {
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                return false;
            }
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        return true;
    }
    
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new UnauthorizedException("User not authenticated");
        }
        
        String username = authentication.getName();
        return findByUsername(username);
    }
}
