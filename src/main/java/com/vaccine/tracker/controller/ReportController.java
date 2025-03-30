package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.response.ReportResponse;
import com.vaccine.tracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * Controller for handling report generation.
 */
@Controller
@RequestMapping("/api/reports")
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Show admin dashboard with reports.
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusMonths(1);
        
        ReportResponse scheduleReport = reportService.generateScheduleReport(startDate, now);
        ReportResponse vaccineReport = reportService.generateVaccineReport(startDate, now);
        ReportResponse revenueReport = reportService.generateRevenueReport(startDate, now);
        
        model.addAttribute("scheduleReport", scheduleReport);
        model.addAttribute("vaccineReport", vaccineReport);
        model.addAttribute("revenueReport", revenueReport);
        
        return "admin/dashboard";
    }

    /**
     * Generate schedule report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the schedule report
     */
    @GetMapping("/schedule")
    @ResponseBody
    public ResponseEntity<ReportResponse> getScheduleReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        ReportResponse report = reportService.generateScheduleReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate vaccine usage report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the vaccine report
     */
    @GetMapping("/vaccine")
    @ResponseBody
    public ResponseEntity<ReportResponse> getVaccineReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        ReportResponse report = reportService.generateVaccineReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate revenue report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the revenue report
     */
    @GetMapping("/revenue")
    @ResponseBody
    public ResponseEntity<ReportResponse> getRevenueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        ReportResponse report = reportService.generateRevenueReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate customer report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the customer report
     */
    @GetMapping("/customer")
    @ResponseBody
    public ResponseEntity<ReportResponse> getCustomerReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        ReportResponse report = reportService.generateCustomerReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate reaction report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the reaction report
     */
    @GetMapping("/reaction")
    @ResponseBody
    public ResponseEntity<ReportResponse> getReactionReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        ReportResponse report = reportService.generateReactionReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate comprehensive report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the comprehensive report
     */
    @GetMapping("/comprehensive")
    @ResponseBody
    public ResponseEntity<ReportResponse> getComprehensiveReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        ReportResponse report = reportService.generateComprehensiveReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
