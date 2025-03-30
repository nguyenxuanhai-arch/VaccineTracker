package com.vaccine.tracker.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO for feedback data responses.
 */
public class FeedbackResponse {
    
    private Long id;
    private Long userId;
    private String userName;
    private Integer rating;
    private String comment;
    private Long scheduleId;
    private String vaccineName;
    private String staffResponse;
    private LocalDateTime respondedAt;
    private LocalDateTime createdAt;
    
    // Constructors
    public FeedbackResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Long getScheduleId() {
        return scheduleId;
    }
    
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    public String getVaccineName() {
        return vaccineName;
    }
    
    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }
    
    public String getStaffResponse() {
        return staffResponse;
    }
    
    public void setStaffResponse(String staffResponse) {
        this.staffResponse = staffResponse;
    }
    
    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }
    
    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Checks if the feedback has a staff response.
     * 
     * @return true if responded
     */
    public boolean hasResponse() {
        return staffResponse != null && !staffResponse.isEmpty();
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }
    
    /**
     * Get formatted response date.
     * 
     * @return formatted date
     */
    public String getFormattedRespondedAt() {
        if (respondedAt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return respondedAt.format(formatter);
    }
    
    /**
     * Generate star rating HTML.
     * 
     * @return HTML star rating
     */
    public String getStarRating() {
        StringBuilder stars = new StringBuilder();
        if (rating != null) {
            for (int i = 1; i <= 5; i++) {
                if (i <= rating) {
                    stars.append("★");
                } else {
                    stars.append("☆");
                }
            }
        }
        return stars.toString();
    }
}
