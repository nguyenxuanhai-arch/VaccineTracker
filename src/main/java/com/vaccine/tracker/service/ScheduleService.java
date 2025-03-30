package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.request.ScheduleRequest;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.ScheduleStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing schedules.
 */
public interface ScheduleService {
    
    /**
     * Find schedule by ID.
     * 
     * @param id the schedule ID
     * @return the schedule
     */
    Schedule findById(Long id);
    
    /**
     * Find schedules by child.
     * 
     * @param child the child
     * @return list of schedules for the child
     */
    List<Schedule> findByChild(Child child);
    
    /**
     * Find schedules by child ID.
     * 
     * @param childId the child ID
     * @return list of schedules for the child
     */
    List<Schedule> findByChildId(Long childId);
    
    /**
     * Find schedules by vaccine.
     * 
     * @param vaccine the vaccine
     * @return list of schedules for the vaccine
     */
    List<Schedule> findByVaccine(Vaccine vaccine);
    
    /**
     * Find schedules by vaccine ID.
     * 
     * @param vaccineId the vaccine ID
     * @return list of schedules for the vaccine
     */
    List<Schedule> findByVaccineId(Long vaccineId);
    
    /**
     * Find schedules by status.
     * 
     * @param status the schedule status
     * @return list of schedules with the status
     */
    List<Schedule> findByStatus(ScheduleStatus status);
    
    /**
     * Create a new schedule.
     * 
     * @param scheduleRequest the schedule request
     * @return the created schedule
     */
    Schedule create(ScheduleRequest scheduleRequest);
    
    /**
     * Update schedule information.
     * 
     * @param id the schedule ID
     * @param scheduleRequest the updated schedule data
     * @return the updated schedule
     */
    Schedule update(Long id, ScheduleRequest scheduleRequest);
    
    /**
     * Delete schedule by ID.
     * 
     * @param id the schedule ID
     */
    void delete(Long id);
    
    /**
     * Find upcoming schedules for a child.
     * 
     * @param childId the child ID
     * @return list of upcoming schedules
     */
    List<Schedule> findUpcomingSchedulesForChild(Long childId);
    
    /**
     * Find schedules within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of schedules in the date range
     */
    List<Schedule> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find schedules within a date range with a specific status.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @param status the schedule status
     * @return list of matching schedules
     */
    List<Schedule> findByDateRangeAndStatus(LocalDateTime startDate, LocalDateTime endDate, ScheduleStatus status);
    
    /**
     * Find schedules for a parent.
     * 
     * @param parentId the parent user ID
     * @return list of schedules for the parent's children
     */
    List<Schedule> findByParentId(Long parentId);
    
    /**
     * Update schedule status.
     * 
     * @param id the schedule ID
     * @param status the new status
     * @param notes any additional notes
     * @return the updated schedule
     */
    Schedule updateStatus(Long id, ScheduleStatus status, String notes);
    
    /**
     * Cancel a schedule.
     * 
     * @param id the schedule ID
     * @param reason the cancellation reason
     * @return the updated schedule
     */
    Schedule cancelSchedule(Long id, String reason);
    
    /**
     * Complete a schedule.
     * 
     * @param id the schedule ID
     * @param notes any completion notes
     * @return the updated schedule
     */
    Schedule completeSchedule(Long id, String notes);
    
    /**
     * Check if the current user has permission to access a schedule.
     * 
     * @param scheduleId the schedule ID
     * @return true if user has permission
     */
    boolean hasPermission(Long scheduleId);
}
