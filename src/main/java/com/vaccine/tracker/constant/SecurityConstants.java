package com.vaccine.tracker.constant;

/**
 * Security related constants used in the application.
 */
public class SecurityConstants {
    
    // Default values - these will be overridden by configuration
    public static final String JWT_SECRET = "defaultJwtSecretKeyThatShouldBeOverriddenInProduction";
    public static final long JWT_EXPIRATION = 86400000; // 1 day in milliseconds
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    
    // Password validation patterns
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    
    // Role prefixes
    public static final String ROLE_PREFIX = "ROLE_";
    
    // Authentication error messages
    public static final String AUTH_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String AUTH_ACCOUNT_DISABLED = "Your account has been disabled";
    public static final String AUTH_INVALID_TOKEN = "Invalid JWT token";
    public static final String AUTH_EXPIRED_TOKEN = "Expired JWT token";
    
    private SecurityConstants() {
        // Private constructor to prevent instantiation
    }
}
