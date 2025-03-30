package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.request.RegisterRequest;
import com.vaccine.tracker.dto.response.UserResponse;
import com.vaccine.tracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for User entity and DTOs.
 */
@Component
public class UserMapper {
    
    @Autowired
    private ChildMapper childMapper;
    
    /**
     * Converts User entity to UserResponse DTO.
     * 
     * @param user the user entity
     * @return the user response
     */
    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setGender(user.getGender());
        response.setCreatedAt(user.getCreatedAt());
        response.setLastLogin(user.getLastLogin());
        response.setEnabled(user.isEnabled());
        
        // Map children if needed
        if (user.getChildren() != null && !user.getChildren().isEmpty()) {
            response.setChildren(user.getChildren().stream()
                    .map(childMapper::toChildResponse)
                    .collect(Collectors.toList()));
        }
        
        return response;
    }
    
    /**
     * Converts list of User entities to list of UserResponse DTOs.
     * 
     * @param users the list of user entities
     * @return the list of user responses
     */
    public List<UserResponse> toUserResponseList(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Converts RegisterRequest DTO to User entity.
     * 
     * @param request the register request
     * @return the user entity
     */
    public User toUser(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        
        return user;
    }
    
    /**
     * Updates User entity from UserResponse DTO.
     * 
     * @param user the user entity to update
     * @param response the user response with updated data
     */
    public void updateUserFromResponse(User user, UserResponse response) {
        if (user == null || response == null) {
            return;
        }
        
        user.setFullName(response.getFullName());
        user.setPhoneNumber(response.getPhoneNumber());
        user.setGender(response.getGender());
        user.setEnabled(response.isEnabled());
    }
}
