package com.vaccine.tracker.enums;

/**
 * Enum representing the different statuses a vaccination schedule can have.
 */
public enum ScheduleStatus {
    
    PENDING("Pending", "Appointment scheduled but not yet confirmed"),
    CONFIRMED("Confirmed", "Appointment confirmed by staff"),
    COMPLETED("Completed", "Vaccination has been administered"),
    CANCELLED("Cancelled", "Appointment has been cancelled"),
    MISSED("Missed", "Patient did not show up for appointment"),
    RESCHEDULED("Rescheduled", "Appointment has been rescheduled");
    
    private final String displayName;
    private final String description;
    
    ScheduleStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the schedule status enum from its name.
     * 
     * @param name the name of the schedule status
     * @return the corresponding ScheduleStatus enum, or PENDING if not found
     */
    public static ScheduleStatus fromName(String name) {
        try {
            return ScheduleStatus.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PENDING;
        }
    }
    
    /**
     * Checks if the status is an active status (not cancelled or missed).
     * 
     * @return true if this is an active status
     */
    public boolean isActive() {
        return this != CANCELLED && this != MISSED;
    }
    
    /**
     * Checks if the schedule can be modified.
     * 
     * @return true if the schedule can be modified
     */
    public boolean isModifiable() {
        return this == PENDING || this == CONFIRMED || this == RESCHEDULED;
    }
}
