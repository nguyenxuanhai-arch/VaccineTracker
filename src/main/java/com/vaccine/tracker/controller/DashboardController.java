package com.vaccine.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard - Child Vaccine Tracker");
        return "dashboard";
    }
    
    // ADMIN ENDPOINTS
    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("title", "User Management - Child Vaccine Tracker");
        return "admin/users";
    }
    
    @GetMapping("/admin/vaccines")
    public String adminVaccines(Model model) {
        model.addAttribute("title", "Vaccine Management - Child Vaccine Tracker");
        return "admin/vaccines";
    }
    
    @GetMapping("/admin/reports")
    public String adminReports(Model model) {
        model.addAttribute("title", "System Reports - Child Vaccine Tracker");
        return "admin/reports";
    }
    
    // PARENT ENDPOINTS
    @GetMapping("/parent/children")
    public String parentChildren(Model model) {
        model.addAttribute("title", "My Children - Child Vaccine Tracker");
        return "parent/children";
    }
    
    @GetMapping("/parent/vaccinations")
    public String parentVaccinations(Model model) {
        model.addAttribute("title", "Vaccination Schedule - Child Vaccine Tracker");
        return "parent/vaccinations";
    }
    
    // HEALTHCARE PROVIDER ENDPOINTS
    @GetMapping("/provider/schedule")
    public String providerSchedule(Model model) {
        model.addAttribute("title", "Today's Schedule - Child Vaccine Tracker");
        return "provider/schedule";
    }
    
    @GetMapping("/provider/record")
    public String providerRecord(Model model) {
        model.addAttribute("title", "Record Vaccination - Child Vaccine Tracker");
        return "provider/record";
    }
}