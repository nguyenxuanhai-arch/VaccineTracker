package com.vaccine.tracker.dto.response;

import com.vaccine.tracker.enums.Gender;
import com.vaccine.tracker.enums.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for user data responses.
 */
public class UserResponse {
    
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Role role;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private boolean enabled;
    private List<ChildResponse> children = new ArrayList<>();
    
    // Constructors
    public UserResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<ChildResponse> getChildren() {
        return children;
    }
    
    public void setChildren(List<ChildResponse> children) {
        this.children = children;
    }
    
    /**
     * Get formatted creation date.
     * 
     * @return formatted date
     */
    public String getFormattedCreatedAt() {
        if (createdAt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return createdAt.format(formatter);
    }
    
    /**
     * Get formatted last login date.
     * 
     * @return formatted date
     */
    public String getFormattedLastLogin() {
        if (lastLogin == null) {
            return "Never";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return lastLogin.format(formatter);
    }
    
    /**
     * Get role display name.
     * 
     * @return role display name
     */
    public String getRoleDisplayName() {
        return role != null ? role.getDisplayName() : "";
    }
    
    /**
     * Get gender display name.
     * 
     * @return gender display name
     */
    public String getGenderDisplayName() {
        return gender != null ? gender.getDisplayName() : "";
    }
    
    /**
     * Check if user has admin privileges.
     * 
     * @return true if admin
     */
    public boolean isAdmin() {
        return role == Role.ROLE_ADMIN;
    }
    
    /**
     * Check if user has staff privileges.
     * 
     * @return true if staff or admin
     */
    public boolean isStaff() {
        return role == Role.ROLE_STAFF || role == Role.ROLE_ADMIN;
    }
    
    /**
     * Get number of children.
     * 
     * @return children count
     */
    public int getChildrenCount() {
        return children != null ? children.size() : 0;
    }
}
