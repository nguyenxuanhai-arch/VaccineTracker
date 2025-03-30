package com.vaccine.tracker.enums;

/**
 * Enum representing the different types of vaccines available in the system.
 */
public enum VaccineType {
    
    MANDATORY("Mandatory", "Required by law vaccines"),
    RECOMMENDED("Recommended", "Vaccines recommended by health authorities"),
    OPTIONAL("Optional", "Optional vaccines for additional protection");
    
    private final String displayName;
    private final String description;
    
    VaccineType(String displayName, String description) {
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
     * Gets the vaccine type enum from its name.
     * 
     * @param name the name of the vaccine type
     * @return the corresponding VaccineType enum, or OPTIONAL if not found
     */
    public static VaccineType fromName(String name) {
        try {
            return VaccineType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OPTIONAL;
        }
    }
}
