package com.vaccine.tracker.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.exception.BadRequestException;

@Component
public class ScheduleValidator {
    
    public void validateScheduleData(Schedule schedule) {
        // Check that child is not null
        if (schedule.getChild() == null) {
            throw new BadRequestException("Child is required");
        }
        
        // Check that vaccine is not null
        if (schedule.getVaccine() == null) {
            throw new BadRequestException("Vaccine is required");
        }
        
        // Check that scheduled date is not null
        if (schedule.getScheduledDate() == null) {
            throw new BadRequestException("Scheduled date is required");
        }
        
        // Check that scheduled date is not in the past
        if (schedule.getScheduledDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Scheduled date cannot be in the past");
        }
        
        // Check that dose number is valid
        if (schedule.getDoseNumber() == null || schedule.getDoseNumber() <= 0) {
            throw new BadRequestException("Dose number must be a positive integer");
        }
    }
}