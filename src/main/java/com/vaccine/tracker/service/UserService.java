package com.vaccine.tracker.service;

import java.util.List;
import java.util.Optional;

import com.vaccine.tracker.dto.RegisterRequest;
import com.vaccine.tracker.entity.User;

public interface UserService {
    
    User registerUser(RegisterRequest registerRequest);
    
    User getCurrentUser();
    
    User getUserById(Long id);
    
    List<User> getAllUsers();
    
    List<User> getUsersByRole(String role);
    
    User updateUser(Long id, User userDetails);
    
    void deleteUser(Long id);
    
    boolean checkUsernameAvailability(String username);
    
    boolean checkEmailAvailability(String email);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
}