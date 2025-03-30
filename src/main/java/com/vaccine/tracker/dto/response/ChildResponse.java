package com.vaccine.tracker.dto.response;

import com.vaccine.tracker.enums.Gender;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for child data responses.
 */
public class ChildResponse {
    
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String medicalNotes;
    private String allergies;
    private Long parentId;
    private String parentName;
    private List<ScheduleResponse> upcomingSchedules = new ArrayList<>();
    
    // Constructors
    public ChildResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getMedicalNotes() {
        return medicalNotes;
    }
    
    public void setMedicalNotes(String medicalNotes) {
        this.medicalNotes = medicalNotes;
    }
    
    public String getAllergies() {
        return allergies;
    }
    
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getParentName() {
        return parentName;
    }
    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    public List<ScheduleResponse> getUpcomingSchedules() {
        return upcomingSchedules;
    }
    
    public void setUpcomingSchedules(List<ScheduleResponse> upcomingSchedules) {
        this.upcomingSchedules = upcomingSchedules;
    }
    
    /**
     * Calculate the age of the child.
     * 
     * @return the age as a formatted string (years and months)
     */
    public String getAge() {
        if (dateOfBirth == null) {
            return "Unknown";
        }
        
        Period period = Period.between(dateOfBirth, LocalDate.now());
        int years = period.getYears();
        int months = period.getMonths();
        
        if (years > 0) {
            return years + " years" + (months > 0 ? ", " + months + " months" : "");
        } else {
            return months + " months";
        }
    }
    
    /**
     * Calculate the age in months.
     * 
     * @return the age in months
     */
    public int getAgeInMonths() {
        if (dateOfBirth == null) {
            return 0;
        }
        
        Period period = Period.between(dateOfBirth, LocalDate.now());
        return period.getYears() * 12 + period.getMonths();
    }
}
