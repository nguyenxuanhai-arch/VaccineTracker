package com.vaccine.tracker.dto.request;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for reaction creation requests.
 */
public class ReactionRequest {
    
    @NotNull(message = "Child ID is required")
    private Long childId;
    
    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;
    
    @NotBlank(message = "Symptoms cannot be blank")
    @Size(min = 3, max = 1000, message = "Symptoms must be between 3 and 1000 characters")
    private String symptoms;
    
    @NotNull(message = "Reaction date is required")
    private LocalDateTime reactionDate;
    
    @NotNull(message = "Severity is required")
    @Min(value = 1, message = "Severity must be at least 1")
    @Max(value = 5, message = "Severity must be at most 5")
    private Integer severity;
    
    @Size(max = 1000, message = "Treatment must be less than 1000 characters")
    private String treatment;
    
    // Getters and Setters
    public Long getChildId() {
        return childId;
    }
    
    public void setChildId(Long childId) {
        this.childId = childId;
    }
    
    public Long getScheduleId() {
        return scheduleId;
    }
    
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    public String getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public LocalDateTime getReactionDate() {
        return reactionDate;
    }
    
    public void setReactionDate(LocalDateTime reactionDate) {
        this.reactionDate = reactionDate;
    }
    
    public Integer getSeverity() {
        return severity;
    }
    
    public void setSeverity(Integer severity) {
        this.severity = severity;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
