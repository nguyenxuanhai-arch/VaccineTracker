package com.vaccine.tracker.dto.response;

import com.vaccine.tracker.enums.ScheduleStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO for schedule data responses.
 */
public class ScheduleResponse {
    
    private Long id;
    private ChildResponse child;
    private VaccineResponse vaccine;
    private LocalDateTime scheduleDate;
    private ScheduleStatus status;
    private String notes;
    private Integer doseNumber;
    private LocalDateTime completedDate;
    private String cancellationReason;
    private boolean canModify;
    
    // Simple data to reduce payload
    private Long childId;
    private String childName;
    private Long vaccineId;
    private String vaccineName;
    
    // Constructors
    public ScheduleResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public ChildResponse getChild() {
        return child;
    }
    
    public void setChild(ChildResponse child) {
        this.child = child;
    }
    
    public VaccineResponse getVaccine() {
        return vaccine;
    }
    
    public void setVaccine(VaccineResponse vaccine) {
        this.vaccine = vaccine;
    }
    
    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public ScheduleStatus getStatus() {
        return status;
    }
    
    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Integer getDoseNumber() {
        return doseNumber;
    }
    
    public void setDoseNumber(Integer doseNumber) {
        this.doseNumber = doseNumber;
    }
    
    public LocalDateTime getCompletedDate() {
        return completedDate;
    }
    
    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
    
    public boolean isCanModify() {
        return canModify;
    }
    
    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
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
    
    public Long getVaccineId() {
        return vaccineId;
    }
    
    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }
    
    public String getVaccineName() {
        return vaccineName;
    }
    
    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }
    
    /**
     * Get formatted schedule date.
     * 
     * @return formatted date and time
     */
    public String getFormattedScheduleDate() {
        if (scheduleDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return scheduleDate.format(formatter);
    }
    
    /**
     * Get status display name.
     * 
     * @return status display name
     */
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "";
    }
    
    /**
     * Check if the schedule is in an active status.
     * 
     * @return true if active
     */
    public boolean isActive() {
        return status != null && status.isActive();
    }
}
