package com.vaccine.tracker.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO for reaction data responses.
 */
public class ReactionResponse {
    
    private Long id;
    private Long childId;
    private String childName;
    private Long scheduleId;
    private String vaccineName;
    private String symptoms;
    private LocalDateTime reactionDate;
    private Integer severity;
    private String treatment;
    private String staffNotes;
    private Boolean resolved;
    private LocalDateTime resolvedDate;
    
    // Constructors
    public ReactionResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getChildId() {
        return childId;
    }
    
    public void setChildId(Long childId) {
        this.childId = childId;
    }
    
    public String getChildName() {
        return childName;
    }
    
    public void setChildName(String childName) {
        this.childName = childName;
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
    
    public String getStaffNotes() {
        return staffNotes;
    }
    
    public void setStaffNotes(String staffNotes) {
        this.staffNotes = staffNotes;
    }
    
    public Boolean getResolved() {
        return resolved;
    }
    
    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
    
    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }
    
    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }
    
    /**
     * Get formatted reaction date.
     * 
     * @return formatted date
     */
    public String getFormattedReactionDate() {
        if (reactionDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return reactionDate.format(formatter);
    }
    
    /**
     * Get formatted resolved date.
     * 
     * @return formatted date
     */
    public String getFormattedResolvedDate() {
        if (resolvedDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return resolvedDate.format(formatter);
    }
    
    /**
     * Get severity level as text.
     * 
     * @return severity text
     */
    public String getSeverityText() {
        if (severity == null) {
            return "Unknown";
        }
        
        switch (severity) {
            case 1: return "Mild";
            case 2: return "Moderate";
            case 3: return "Significant";
            case 4: return "Severe";
            case 5: return "Critical";
            default: return "Unknown";
        }
    }
    
    /**
     * Check if the reaction is severe.
     * 
     * @return true if severe
     */
    public boolean isSevere() {
        return severity != null && severity >= 4;
    }
    
    /**
     * Get CSS class based on severity.
     * 
     * @return CSS class
     */
    public String getSeverityCssClass() {
        if (severity == null) {
            return "severity-unknown";
        }
        
        switch (severity) {
            case 1: return "severity-mild";
            case 2: return "severity-moderate";
            case 3: return "severity-significant";
            case 4: return "severity-severe";
            case 5: return "severity-critical";
            default: return "severity-unknown";
        }
    }
}
