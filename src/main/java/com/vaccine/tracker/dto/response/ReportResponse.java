package com.vaccine.tracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO for report data responses.
 */
public class ReportResponse {
    
    private LocalDateTime reportGeneratedAt;
    private String reportType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    // Schedule report data
    private long totalSchedules;
    private long completedSchedules;
    private long pendingSchedules;
    private long cancelledSchedules;
    private long missedSchedules;
    private Map<String, Long> schedulesByVaccine = new LinkedHashMap<>();
    private Map<String, Long> schedulesByAgeGroup = new LinkedHashMap<>();
    
    // Vaccine report data
    private long totalVaccinesAdministered;
    private Map<String, Long> vaccinesByType = new LinkedHashMap<>();
    private Map<String, Long> mostAdministeredVaccines = new LinkedHashMap<>();
    
    // Revenue report data
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private Map<String, BigDecimal> revenueByMonth = new LinkedHashMap<>();
    private Map<String, BigDecimal> revenueByVaccine = new LinkedHashMap<>();
    
    // Reaction report data
    private long totalReactions;
    private long severeReactions;
    private Map<String, Long> reactionsByVaccine = new LinkedHashMap<>();
    private Map<Integer, Long> reactionsBySeverity = new LinkedHashMap<>();
    
    // Customer report data
    private long totalCustomers;
    private long newCustomers;
    private double averageRating;
    private List<FeedbackResponse> recentFeedback;
    
    // Constructors
    public ReportResponse() {
        this.reportGeneratedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public LocalDateTime getReportGeneratedAt() {
        return reportGeneratedAt;
    }
    
    public void setReportGeneratedAt(LocalDateTime reportGeneratedAt) {
        this.reportGeneratedAt = reportGeneratedAt;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public long getTotalSchedules() {
        return totalSchedules;
    }
    
    public void setTotalSchedules(long totalSchedules) {
        this.totalSchedules = totalSchedules;
    }
    
    public long getCompletedSchedules() {
        return completedSchedules;
    }
    
    public void setCompletedSchedules(long completedSchedules) {
        this.completedSchedules = completedSchedules;
    }
    
    public long getPendingSchedules() {
        return pendingSchedules;
    }
    
    public void setPendingSchedules(long pendingSchedules) {
        this.pendingSchedules = pendingSchedules;
    }
    
    public long getCancelledSchedules() {
        return cancelledSchedules;
    }
    
    public void setCancelledSchedules(long cancelledSchedules) {
        this.cancelledSchedules = cancelledSchedules;
    }
    
    public long getMissedSchedules() {
        return missedSchedules;
    }
    
    public void setMissedSchedules(long missedSchedules) {
        this.missedSchedules = missedSchedules;
    }
    
    public Map<String, Long> getSchedulesByVaccine() {
        return schedulesByVaccine;
    }
    
    public void setSchedulesByVaccine(Map<String, Long> schedulesByVaccine) {
        this.schedulesByVaccine = schedulesByVaccine;
    }
    
    public Map<String, Long> getSchedulesByAgeGroup() {
        return schedulesByAgeGroup;
    }
    
    public void setSchedulesByAgeGroup(Map<String, Long> schedulesByAgeGroup) {
        this.schedulesByAgeGroup = schedulesByAgeGroup;
    }
    
    public long getTotalVaccinesAdministered() {
        return totalVaccinesAdministered;
    }
    
    public void setTotalVaccinesAdministered(long totalVaccinesAdministered) {
        this.totalVaccinesAdministered = totalVaccinesAdministered;
    }
    
    public Map<String, Long> getVaccinesByType() {
        return vaccinesByType;
    }
    
    public void setVaccinesByType(Map<String, Long> vaccinesByType) {
        this.vaccinesByType = vaccinesByType;
    }
    
    public Map<String, Long> getMostAdministeredVaccines() {
        return mostAdministeredVaccines;
    }
    
    public void setMostAdministeredVaccines(Map<String, Long> mostAdministeredVaccines) {
        this.mostAdministeredVaccines = mostAdministeredVaccines;
    }
    
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }
    
    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }
    
    public Map<String, BigDecimal> getRevenueByMonth() {
        return revenueByMonth;
    }
    
    public void setRevenueByMonth(Map<String, BigDecimal> revenueByMonth) {
        this.revenueByMonth = revenueByMonth;
    }
    
    public Map<String, BigDecimal> getRevenueByVaccine() {
        return revenueByVaccine;
    }
    
    public void setRevenueByVaccine(Map<String, BigDecimal> revenueByVaccine) {
        this.revenueByVaccine = revenueByVaccine;
    }
    
    public long getTotalReactions() {
        return totalReactions;
    }
    
    public void setTotalReactions(long totalReactions) {
        this.totalReactions = totalReactions;
    }
    
    public long getSevereReactions() {
        return severeReactions;
    }
    
    public void setSevereReactions(long severeReactions) {
        this.severeReactions = severeReactions;
    }
    
    public Map<String, Long> getReactionsByVaccine() {
        return reactionsByVaccine;
    }
    
    public void setReactionsByVaccine(Map<String, Long> reactionsByVaccine) {
        this.reactionsByVaccine = reactionsByVaccine;
    }
    
    public Map<Integer, Long> getReactionsBySeverity() {
        return reactionsBySeverity;
    }
    
    public void setReactionsBySeverity(Map<Integer, Long> reactionsBySeverity) {
        this.reactionsBySeverity = reactionsBySeverity;
    }
    
    public long getTotalCustomers() {
        return totalCustomers;
    }
    
    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }
    
    public long getNewCustomers() {
        return newCustomers;
    }
    
    public void setNewCustomers(long newCustomers) {
        this.newCustomers = newCustomers;
    }
    
    public double getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    
    public List<FeedbackResponse> getRecentFeedback() {
        return recentFeedback;
    }
    
    public void setRecentFeedback(List<FeedbackResponse> recentFeedback) {
        this.recentFeedback = recentFeedback;
    }
    
    /**
     * Get completion rate as percentage.
     * 
     * @return completion rate
     */
    public double getCompletionRate() {
        if (totalSchedules == 0) {
            return 0;
        }
        return (double) completedSchedules / totalSchedules * 100;
    }
    
    /**
     * Get cancellation rate as percentage.
     * 
     * @return cancellation rate
     */
    public double getCancellationRate() {
        if (totalSchedules == 0) {
            return 0;
        }
        return (double) cancelledSchedules / totalSchedules * 100;
    }
    
    /**
     * Get severe reaction rate.
     * 
     * @return severe reaction rate
     */
    public double getSevereReactionRate() {
        if (totalReactions == 0) {
            return 0;
        }
        return (double) severeReactions / totalReactions * 100;
    }
}
