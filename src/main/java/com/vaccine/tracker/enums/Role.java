package com.vaccine.tracker.enums;

/**
 * Enum representing user roles in the system.
 * Each role has specific permissions and access levels.
 */
public enum Role {
    
    ROLE_GUEST("Guest"),
    ROLE_CUSTOMER("Customer"),
    ROLE_STAFF("Staff"),
    ROLE_ADMIN("Admin");
    
    private final String displayName;
    
    Role(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Gets the role enum from its name.
     * 
     * @param name the name of the role
     * @return the corresponding Role enum, or ROLE_GUEST if not found
     */
    public static Role fromName(String name) {
        try {
            return Role.valueOf("ROLE_" + name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ROLE_GUEST;
        }
    }
    
    /**
     * Checks if this role has greater or equal privileges than the specified role.
     * 
     * @param role the role to compare against
     * @return true if this role has greater or equal privileges
     */
    public boolean hasAtLeastPrivilegesOf(Role role) {
        return this.ordinal() >= role.ordinal();
    }
    
    /**
     * Gets the simple name of the role without the ROLE_ prefix.
     * 
     * @return the simple name
     */
    public String getSimpleName() {
        return name().replace("ROLE_", "");
    }
}
