package com.vaccine.tracker.dto.request;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * DTO for schedule creation and update requests.
 */
public class ScheduleRequest {
    
    @NotNull(message = "Child ID is required")
    private Long childId;
    
    @NotNull(message = "Vaccine ID is required")
    private Long vaccineId;
    
    @NotNull(message = "Schedule date is required")
    @Future(message = "Schedule date must be in the future")
    private LocalDateTime scheduleDate;
    
    private Integer doseNumber;
    
    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;
    
    // Getters and Setters
    public Long getChildId() {
        return childId;
    }
    
    public void setChildId(Long childId) {
        this.childId = childId;
    }
    
    public Long getVaccineId() {
        return vaccineId;
    }
    
    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }
    
    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public Integer getDoseNumber() {
        return doseNumber;
    }
    
    public void setDoseNumber(Integer doseNumber) {
        this.doseNumber = doseNumber;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
