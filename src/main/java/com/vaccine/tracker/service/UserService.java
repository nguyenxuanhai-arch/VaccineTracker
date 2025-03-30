package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.request.RegisterRequest;
import com.vaccine.tracker.dto.response.UserResponse;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;

import java.util.List;

/**
 * Service for managing users.
 */
public interface UserService {
    
    /**
     * Find user by ID.
     * 
     * @param id the user ID
     * @return the user
     */
    User findById(Long id);
    
    /**
     * Find user by username.
     * 
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);
    
    /**
     * Find user by email.
     * 
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);
    
    /**
     * Check if username exists.
     * 
     * @param username the username
     * @return true if exists
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if email exists.
     * 
     * @param email the email
     * @return true if exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Register a new user.
     * 
     * @param registerRequest the registration request
     * @return the created user
     */
    User register(RegisterRequest registerRequest);
    
    /**
     * Update user information.
     * 
     * @param id the user ID
     * @param userResponse the updated user data
     * @return the updated user
     */
    User update(Long id, UserResponse userResponse);
    
    /**
     * Delete user by ID.
     * 
     * @param id the user ID
     */
    void delete(Long id);
    
    /**
     * Find all users.
     * 
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Find users by role.
     * 
     * @param role the role
     * @return list of users with the role
     */
    List<User> findByRole(Role role);
    
    /**
     * Find users by name containing keyword.
     * 
     * @param keyword the search keyword
     * @return list of matching users
     */
    List<User> findByFullNameContaining(String keyword);
    
    /**
     * Update user's last login time.
     * 
     * @param username the username
     */
    void updateLastLogin(String username);
    
    /**
     * Enable or disable a user.
     * 
     * @param id the user ID
     * @param enabled the enabled status
     * @return the updated user
     */
    User updateEnabledStatus(Long id, boolean enabled);
    
    /**
     * Change user's role.
     * 
     * @param id the user ID
     * @param role the new role
     * @return the updated user
     */
    User changeRole(Long id, Role role);
    
    /**
     * Change user's password.
     * 
     * @param id the user ID
     * @param currentPassword the current password
     * @param newPassword the new password
     * @return true if successful
     */
    boolean changePassword(Long id, String currentPassword, String newPassword);
    
    /**
     * Get current authenticated user.
     * 
     * @return the current user
     */
    User getCurrentUser();
}
