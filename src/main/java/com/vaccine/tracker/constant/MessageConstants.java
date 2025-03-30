package com.vaccine.tracker.constant;

/**
 * Message constants used throughout the application for consistent messaging.
 */
public class MessageConstants {
    
    // Success messages
    public static final String SUCCESS_REGISTER = "User registered successfully";
    public static final String SUCCESS_LOGIN = "Login successful";
    public static final String SUCCESS_LOGOUT = "Logout successful";
    public static final String SUCCESS_UPDATE = "Updated successfully";
    public static final String SUCCESS_DELETE = "Deleted successfully";
    public static final String SUCCESS_CREATE = "Created successfully";
    
    // Error messages
    public static final String ERROR_USER_EXISTS = "Username is already taken";
    public static final String ERROR_EMAIL_EXISTS = "Email is already in use";
    public static final String ERROR_NOT_FOUND = "Resource not found";
    public static final String ERROR_ACCESS_DENIED = "You don't have permission to access this resource";
    public static final String ERROR_VALIDATION = "Validation error";
    public static final String ERROR_SERVER = "Internal server error";
    public static final String ERROR_BAD_REQUEST = "Bad request";
    
    // Child related messages
    public static final String CHILD_NOT_FOUND = "Child not found";
    public static final String CHILD_ALREADY_EXISTS = "Child with this information already exists";
    
    // Vaccine related messages
    public static final String VACCINE_NOT_FOUND = "Vaccine not found";
    
    // Schedule related messages
    public static final String SCHEDULE_NOT_FOUND = "Schedule not found";
    public static final String SCHEDULE_ALREADY_EXISTS = "Schedule already exists for this time";
    public static final String SCHEDULE_CONFLICT = "Schedule conflicts with existing appointment";
    
    // Payment related messages
    public static final String PAYMENT_SUCCESSFUL = "Payment processed successfully";
    public static final String PAYMENT_FAILED = "Payment processing failed";
    
    private MessageConstants() {
        // Private constructor to prevent instantiation
    }
}
