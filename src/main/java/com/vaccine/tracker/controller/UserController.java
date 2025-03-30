package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.dto.response.UserResponse;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.mapper.UserMapper;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for handling user operations.
 */
@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * Get the profile page for the current user.
     */
    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", userMapper.toUserResponse(currentUser));
        return "user/profile";
    }

    /**
     * Get the current user's profile details.
     *
     * @return the user details
     */
    @GetMapping("/profile/details")
    @ResponseBody
    public ResponseEntity<UserResponse> getProfile() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toUserResponse(currentUser));
    }

    /**
     * Update the current user's profile.
     *
     * @param userResponse updated user details
     * @return success message
     */
    @PutMapping("/profile")
    @ResponseBody
    public ResponseEntity<MessageResponse> updateProfile(@Valid @RequestBody UserResponse userResponse) {
        User currentUser = userService.getCurrentUser();
        userService.update(currentUser.getId(), userResponse);
        return ResponseEntity.ok(new MessageResponse("Profile updated successfully"));
    }

    /**
     * Change the current user's password.
     *
     * @param currentPassword the current password
     * @param newPassword the new password
     * @return success or error message
     */
    @PostMapping("/change-password")
    @ResponseBody
    public ResponseEntity<MessageResponse> changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        
        User currentUser = userService.getCurrentUser();
        boolean success = userService.changePassword(currentUser.getId(), currentPassword, newPassword);
        
        if (success) {
            return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Current password is incorrect", false));
        }
    }

    /**
     * Get all users (admin only).
     *
     * @return list of all users
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userMapper.toUserResponseList(users));
    }

    /**
     * Get a specific user by ID (admin only).
     *
     * @param id the user ID
     * @return the user details
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    /**
     * Update a user's details (admin only).
     *
     * @param id the user ID
     * @param userResponse updated user details
     * @return success message
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserResponse userResponse) {
        
        userService.update(id, userResponse);
        return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    }

    /**
     * Delete a user (admin only).
     *
     * @param id the user ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    /**
     * Change a user's role (admin only).
     *
     * @param id the user ID
     * @param role the new role
     * @return success message
     */
    @PostMapping("/{id}/change-role")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> changeRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        
        userService.changeRole(id, role);
        return ResponseEntity.ok(new MessageResponse("User role updated successfully"));
    }

    /**
     * Enable or disable a user account (admin only).
     *
     * @param id the user ID
     * @param enabled the enabled status
     * @return success message
     */
    @PostMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam boolean enabled) {
        
        userService.updateEnabledStatus(id, enabled);
        String message = enabled ? "User account enabled" : "User account disabled";
        return ResponseEntity.ok(new MessageResponse(message));
    }

    /**
     * Find users by role (admin only).
     *
     * @param role the role to filter by
     * @return list of matching users
     */
    @GetMapping("/by-role")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<UserResponse>> getUsersByRole(@RequestParam Role role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.ok(userMapper.toUserResponseList(users));
    }

    /**
     * Search users by name (admin only).
     *
     * @param keyword the search keyword
     * @return list of matching users
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String keyword) {
        List<User> users = userService.findByFullNameContaining(keyword);
        return ResponseEntity.ok(userMapper.toUserResponseList(users));
    }
}
