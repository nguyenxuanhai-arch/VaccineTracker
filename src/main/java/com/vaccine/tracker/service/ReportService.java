package com.vaccine.tracker.service;

import java.time.LocalDate;
import java.util.Map;

public interface ReportService {
    
    // Get vaccination coverage statistics
    Map<String, Object> getVaccinationCoverageStats();
    
    // Get schedule statistics for a date range
    Map<String, Object> getScheduleStatsByDateRange(LocalDate startDate, LocalDate endDate);
    
    // Get child vaccination status report
    Map<String, Object> getChildVaccinationStatusReport(Long childId);
    
    // Get monthly vaccination reports
    Map<String, Object> getMonthlyVaccinationReport(int year, int month);
    
    // Get provider performance report
    Map<String, Object> getProviderPerformanceReport(Long providerId);
    
    // Get vaccine inventory report
    Map<String, Object> getVaccineInventoryReport();
    
    // Get missed appointments report
    Map<String, Object> getMissedAppointmentsReport();
    
    // Export vaccination data to CSV
    String exportVaccinationDataToCsv();
}