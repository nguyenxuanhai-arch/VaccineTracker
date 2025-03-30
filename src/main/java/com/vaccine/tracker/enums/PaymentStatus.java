package com.vaccine.tracker.enums;

/**
 * Enum representing the different statuses a payment can have in the system.
 */
public enum PaymentStatus {
    
    PENDING("Pending", "Payment has been initiated but not completed"),
    COMPLETED("Completed", "Payment has been successfully processed"),
    FAILED("Failed", "Payment processing has failed"),
    REFUNDED("Refunded", "Payment has been refunded");
    
    private final String displayName;
    private final String description;
    
    PaymentStatus(String displayName, String description) {
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
     * Gets the payment status enum from its name.
     * 
     * @param name the name of the payment status
     * @return the corresponding PaymentStatus enum, or PENDING if not found
     */
    public static PaymentStatus fromName(String name) {
        try {
            return PaymentStatus.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PENDING;
        }
    }
    
    /**
     * Checks if the payment is successful.
     * 
     * @return true if payment is successful
     */
    public boolean isSuccessful() {
        return this == COMPLETED;
    }
    
    /**
     * Checks if the payment can be processed further.
     * 
     * @return true if payment can be processed
     */
    public boolean isProcessable() {
        return this == PENDING;
    }
}
