package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.request.ScheduleRequest;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.enums.ScheduleStatus;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.ScheduleMapper;
import com.vaccine.tracker.repository.ScheduleRepository;
import com.vaccine.tracker.service.ChildService;
import com.vaccine.tracker.service.ScheduleService;
import com.vaccine.tracker.service.UserService;
import com.vaccine.tracker.service.VaccineService;
import com.vaccine.tracker.validator.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of ScheduleService interface.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private ChildService childService;
    
    @Autowired
    private VaccineService vaccineService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ScheduleMapper scheduleMapper;
    
    @Autowired
    private ScheduleValidator scheduleValidator;
    
    @Override
    public Schedule findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
        
        // Check permission
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to access this schedule");
        }
        
        return schedule;
    }
    
    @Override
    public List<Schedule> findByChild(Child child) {
        // Check permission
        if (!childService.hasPermission(child.getId())) {
            throw new UnauthorizedException("You don't have permission to access this child's schedules");
        }
        
        return scheduleRepository.findByChild(child);
    }
    
    @Override
    public List<Schedule> findByChildId(Long childId) {
        // Check permission
        if (!childService.hasPermission(childId)) {
            throw new UnauthorizedException("You don't have permission to access this child's schedules");
        }
        
        return scheduleRepository.findByChildId(childId);
    }
    
    @Override
    public List<Schedule> findByVaccine(Vaccine vaccine) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all schedules for a vaccine
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return scheduleRepository.findByVaccine(vaccine);
        } else {
            // For customers, filter to only show their children's schedules
            return scheduleRepository.findByVaccine(vaccine).stream()
                    .filter(schedule -> schedule.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Schedule> findByVaccineId(Long vaccineId) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all schedules for a vaccine
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return scheduleRepository.findByVaccineId(vaccineId);
        } else {
            // For customers, filter to only show their children's schedules
            return scheduleRepository.findByVaccineId(vaccineId).stream()
                    .filter(schedule -> schedule.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Schedule> findByStatus(ScheduleStatus status) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all schedules by status
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return scheduleRepository.findByStatus(status);
        } else {
            // For customers, filter to only show their children's schedules
            return scheduleRepository.findByStatus(status).stream()
                    .filter(schedule -> schedule.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    @Transactional
    public Schedule create(ScheduleRequest scheduleRequest) {
        // Validate the request
        scheduleValidator.validate(scheduleRequest);
        
        Child child = childService.findById(scheduleRequest.getChildId());
        Vaccine vaccine = vaccineService.findById(scheduleRequest.getVaccineId());
        
        // Check if vaccine is suitable for child's age
        int childAgeMonths = child.getAge() * 12; // Approximate conversion
        if (!vaccine.isSuitableForAge(childAgeMonths)) {
            throw new BadRequestException("Vaccine is not suitable for child's age");
        }
        
        // Check for schedule conflicts
        checkScheduleConflicts(child, scheduleRequest.getScheduleDate(), null);
        
        // Create and save new schedule
        Schedule schedule = scheduleMapper.toSchedule(scheduleRequest, child, vaccine);
        
        return scheduleRepository.save(schedule);
    }
    
    @Override
    @Transactional
    public Schedule update(Long id, ScheduleRequest scheduleRequest) {
        // Validate the request
        scheduleValidator.validate(scheduleRequest);
        
        Schedule schedule = findById(id);
        
        // Check if schedule can be modified
        if (!schedule.isModifiable()) {
            throw new BadRequestException("Schedule cannot be modified (status: " + schedule.getStatus() + ")");
        }
        
        Child child = childService.findById(scheduleRequest.getChildId());
        Vaccine vaccine = vaccineService.findById(scheduleRequest.getVaccineId());
        
        // Check if vaccine is suitable for child's age
        int childAgeMonths = child.getAge() * 12; // Approximate conversion
        if (!vaccine.isSuitableForAge(childAgeMonths)) {
            throw new BadRequestException("Vaccine is not suitable for child's age");
        }
        
        // Check for schedule conflicts
        checkScheduleConflicts(child, scheduleRequest.getScheduleDate(), id);
        
        // Update schedule properties
        scheduleMapper.updateScheduleFromRequest(schedule, scheduleRequest, child, vaccine);
        
        return scheduleRepository.save(schedule);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Schedule schedule = findById(id);
        
        // Check if schedule can be deleted
        if (!schedule.isModifiable()) {
            throw new BadRequestException("Schedule cannot be deleted (status: " + schedule.getStatus() + ")");
        }
        
        // Check permission
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && 
            currentUser.getRole() != Role.ROLE_STAFF && 
            !schedule.getChild().getParent().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to delete this schedule");
        }
        
        scheduleRepository.delete(schedule);
    }
    
    @Override
    public List<Schedule> findUpcomingSchedulesForChild(Long childId) {
        // Check permission
        if (!childService.hasPermission(childId)) {
            throw new UnauthorizedException("You don't have permission to access this child's schedules");
        }
        
        Child child = childService.findById(childId);
        LocalDateTime now = LocalDateTime.now();
        
        return scheduleRepository.findByChildAndScheduleDateAfterAndStatusNot(
                child, now, ScheduleStatus.CANCELLED);
    }
    
    @Override
    public List<Schedule> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all schedules in a date range
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return scheduleRepository.findByScheduleDateBetween(startDate, endDate);
        } else {
            // For customers, filter to only show their children's schedules
            return scheduleRepository.findByScheduleDateBetween(startDate, endDate).stream()
                    .filter(schedule -> schedule.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Schedule> findByDateRangeAndStatus(LocalDateTime startDate, LocalDateTime endDate, ScheduleStatus status) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all schedules in a date range with status
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return scheduleRepository.findByScheduleDateBetweenAndStatus(startDate, endDate, status);
        } else {
            // For customers, filter to only show their children's schedules
            return scheduleRepository.findByScheduleDateBetweenAndStatus(startDate, endDate, status).stream()
                    .filter(schedule -> schedule.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Schedule> findByParentId(Long parentId) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff, admin, or the parent can see the parent's children's schedules
        if (currentUser.getRole() == Role.ROLE_ADMIN || 
            currentUser.getRole() == Role.ROLE_STAFF || 
            currentUser.getId().equals(parentId)) {
            return scheduleRepository.findByParentId(parentId);
        } else {
            throw new UnauthorizedException("You don't have permission to access these schedules");
        }
    }
    
    @Override
    @Transactional
    public Schedule updateStatus(Long id, ScheduleStatus status, String notes) {
        Schedule schedule = findById(id);
        
        // Only staff and admin can update schedule status
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can update schedule status");
        }
        
        // Check if schedule can be modified
        if (!schedule.isModifiable() && status != ScheduleStatus.CANCELLED) {
            throw new BadRequestException("Schedule cannot be modified (status: " + schedule.getStatus() + ")");
        }
        
        schedule.setStatus(status);
        if (notes != null && !notes.trim().isEmpty()) {
            schedule.setNotes(notes);
        }
        
        return scheduleRepository.save(schedule);
    }
    
    @Override
    @Transactional
    public Schedule cancelSchedule(Long id, String reason) {
        Schedule schedule = findById(id);
        
        // Check if schedule can be cancelled
        if (!schedule.isModifiable()) {
            throw new BadRequestException("Schedule cannot be cancelled (status: " + schedule.getStatus() + ")");
        }
        
        // Check permission
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && 
            currentUser.getRole() != Role.ROLE_STAFF && 
            !schedule.getChild().getParent().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to cancel this schedule");
        }
        
        schedule.cancel(reason);
        
        return scheduleRepository.save(schedule);
    }
    
    @Override
    @Transactional
    public Schedule completeSchedule(Long id, String notes) {
        Schedule schedule = findById(id);
        
        // Only staff and admin can complete schedules
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can complete schedules");
        }
        
        schedule.setStatus(ScheduleStatus.COMPLETED);
        schedule.setCompletedDate(LocalDateTime.now());
        
        if (notes != null && !notes.trim().isEmpty()) {
            schedule.setNotes(notes);
        }
        
        return scheduleRepository.save(schedule);
    }
    
    @Override
    public boolean hasPermission(Long scheduleId) {
        User currentUser = userService.getCurrentUser();
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));
        
        // Admin and staff can access any schedule
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return true;
        }
        
        // Customers can only access their children's schedules
        return schedule.getChild().getParent().getId().equals(currentUser.getId());
    }
    
    /**
     * Check for schedule conflicts.
     * 
     * @param child the child
     * @param scheduleDate the proposed schedule date
     * @param excludeScheduleId schedule ID to exclude from conflict check (for updates)
     */
    private void checkScheduleConflicts(Child child, LocalDateTime scheduleDate, Long excludeScheduleId) {
        // Check for conflicts (e.g., another schedule at the same time)
        // This is a simplified implementation - in a real system, you'd check for time slot conflicts
        LocalDateTime startWindow = scheduleDate.minusHours(1);
        LocalDateTime endWindow = scheduleDate.plusHours(1);
        
        List<Schedule> conflictingSchedules = scheduleRepository.findByChildAndScheduleDateAfterAndStatusNot(
                child, startWindow, ScheduleStatus.CANCELLED);
        
        boolean hasConflict = conflictingSchedules.stream()
                .anyMatch(s -> {
                    boolean timeConflict = s.getScheduleDate().isAfter(startWindow) && 
                                          s.getScheduleDate().isBefore(endWindow);
                    boolean notSameSchedule = excludeScheduleId == null || !s.getId().equals(excludeScheduleId);
                    return timeConflict && notSameSchedule;
                });
        
        if (hasConflict) {
            throw new BadRequestException("Schedule conflicts with an existing appointment");
        }
    }
}
