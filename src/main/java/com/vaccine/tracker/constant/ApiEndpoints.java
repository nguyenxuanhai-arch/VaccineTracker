package com.vaccine.tracker.constant;

/**
 * API endpoint constants used in the application.
 */
public class ApiEndpoints {
    
    // Base API endpoints
    public static final String API_BASE = "/api";
    
    // Auth endpoints
    public static final String AUTH_BASE = API_BASE + "/auth";
    public static final String AUTH_REGISTER = AUTH_BASE + "/register";
    public static final String AUTH_LOGIN = AUTH_BASE + "/login";
    public static final String AUTH_LOGOUT = AUTH_BASE + "/logout";
    
    // User endpoints
    public static final String USERS_BASE = API_BASE + "/users";
    public static final String USERS_PROFILE = USERS_BASE + "/profile";
    
    // Children endpoints
    public static final String CHILDREN_BASE = API_BASE + "/children";
    
    // Vaccine endpoints
    public static final String VACCINES_BASE = API_BASE + "/vaccines";
    
    // Schedule endpoints
    public static final String SCHEDULES_BASE = API_BASE + "/schedules";
    
    // Feedback endpoints
    public static final String FEEDBACKS_BASE = API_BASE + "/feedbacks";
    
    // Reaction endpoints
    public static final String REACTIONS_BASE = API_BASE + "/reactions";
    
    // Payment and order endpoints
    public static final String ORDERS_BASE = API_BASE + "/orders";
    public static final String PAYMENTS_BASE = API_BASE + "/payments";
    
    // Report endpoints
    public static final String REPORTS_BASE = API_BASE + "/reports";
    public static final String REPORTS_SCHEDULE = REPORTS_BASE + "/schedule";
    public static final String REPORTS_VACCINE = REPORTS_BASE + "/vaccine";
    public static final String REPORTS_REVENUE = REPORTS_BASE + "/revenue";
    
    private ApiEndpoints() {
        // Private constructor to prevent instantiation
    }
}
