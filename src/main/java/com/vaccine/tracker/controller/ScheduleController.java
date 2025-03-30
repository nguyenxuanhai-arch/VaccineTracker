package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.request.ScheduleRequest;
import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.dto.response.ScheduleResponse;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.enums.ScheduleStatus;
import com.vaccine.tracker.mapper.ScheduleMapper;
import com.vaccine.tracker.service.ScheduleService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for handling schedule operations.
 */
@Controller
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleMapper scheduleMapper;

    /**
     * Show the list of schedules page.
     */
    @GetMapping
    public String showSchedulesListPage(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "schedule/list";
    }

    /**
     * Show the create schedule page.
     */
    @GetMapping("/create")
    public String showCreateSchedulePage(Model model) {
        model.addAttribute("scheduleRequest", new ScheduleRequest());
        return "schedule/create";
    }

    /**
     * Get all schedules for current user's children.
     *
     * @return list of schedules
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<ScheduleResponse>> getSchedules() {
        Long parentId = userService.getCurrentUser().getId();
        List<Schedule> schedules = scheduleService.findByParentId(parentId);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponseList(schedules));
    }

    /**
     * Get a specific schedule by ID.
     *
     * @param id the schedule ID
     * @return the schedule details
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleService.findById(id);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponse(schedule));
    }

    /**
     * Create a new schedule.
     *
     * @param scheduleRequest the schedule details
     * @return the created schedule
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleService.create(scheduleRequest);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponse(schedule));
    }

    /**
     * Update a schedule.
     *
     * @param id the schedule ID
     * @param scheduleRequest updated schedule details
     * @return the updated schedule
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequest scheduleRequest) {
        
        Schedule schedule = scheduleService.update(id, scheduleRequest);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponse(schedule));
    }

    /**
     * Delete a schedule.
     *
     * @param id the schedule ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MessageResponse> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Schedule deleted successfully"));
    }

    /**
     * Get schedules for a specific child.
     *
     * @param childId the child ID
     * @return list of schedules
     */
    @GetMapping("/child/{childId}")
    @ResponseBody
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByChild(@PathVariable Long childId) {
        List<Schedule> schedules = scheduleService.findByChildId(childId);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponseList(schedules));
    }

    /**
     * Get upcoming schedules for a child.
     *
     * @param childId the child ID
     * @return list of upcoming schedules
     */
    @GetMapping("/upcoming/{childId}")
    @ResponseBody
    public ResponseEntity<List<ScheduleResponse>> getUpcomingSchedules(@PathVariable Long childId) {
        List<Schedule> schedules = scheduleService.findUpcomingSchedulesForChild(childId);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponseList(schedules));
    }

    /**
     * Get schedules by status.
     *
     * @param status the schedule status
     * @return list of matching schedules
     */
    @GetMapping("/by-status")
    @ResponseBody
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByStatus(@RequestParam ScheduleStatus status) {
        List<Schedule> schedules = scheduleService.findByStatus(status);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponseList(schedules));
    }

    /**
     * Get schedules within a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of schedules in the date range
     */
    @GetMapping("/by-date-range")
    @ResponseBody
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<Schedule> schedules = scheduleService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponseList(schedules));
    }

    /**
     * Update a schedule's status (staff/admin only).
     *
     * @param id the schedule ID
     * @param status the new status
     * @param notes optional notes
     * @return the updated schedule
     */
    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ScheduleStatus status,
            @RequestParam(required = false) String notes) {
        
        Schedule schedule = scheduleService.updateStatus(id, status, notes);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponse(schedule));
    }

    /**
     * Cancel a schedule.
     *
     * @param id the schedule ID
     * @param reason the cancellation reason
     * @return the updated schedule
     */
    @PostMapping("/{id}/cancel")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> cancelSchedule(
            @PathVariable Long id,
            @RequestParam String reason) {
        
        Schedule schedule = scheduleService.cancelSchedule(id, reason);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponse(schedule));
    }

    /**
     * Complete a schedule (staff/admin only).
     *
     * @param id the schedule ID
     * @param notes optional completion notes
     * @return the updated schedule
     */
    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<ScheduleResponse> completeSchedule(
            @PathVariable Long id,
            @RequestParam(required = false) String notes) {
        
        Schedule schedule = scheduleService.completeSchedule(id, notes);
        return ResponseEntity.ok(scheduleMapper.toScheduleResponse(schedule));
    }
}
