package com.vaccine.tracker.service;

import java.time.LocalDate;
import java.util.List;

import com.vaccine.tracker.entity.Schedule;

public interface ScheduleService {
    
    Schedule createSchedule(Schedule schedule);
    
    Schedule getScheduleById(Long id);
    
    List<Schedule> getAllSchedules();
    
    List<Schedule> getSchedulesByChildId(Long childId);
    
    List<Schedule> getSchedulesByParentId(Long parentId);
    
    List<Schedule> getSchedulesByProviderId(Long providerId);
    
    List<Schedule> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<Schedule> getSchedulesByStatus(Schedule.ScheduleStatus status);
    
    Schedule updateSchedule(Long id, Schedule scheduleDetails);
    
    void deleteSchedule(Long id);
    
    List<Schedule> getUpcomingSchedules(Long parentId);
    
    List<Schedule> getOverdueSchedules(Long parentId);
}