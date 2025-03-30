package com.vaccine.tracker.enums;

/**
 * Enum representing gender options for users and children in the system.
 */
public enum Gender {
    
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");
    
    private final String displayName;
    
    Gender(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Gets the gender enum from its name.
     * 
     * @param name the name of the gender
     * @return the corresponding Gender enum, or OTHER if not found
     */
    public static Gender fromName(String name) {
        try {
            return Gender.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}
