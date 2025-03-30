package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.request.ReactionRequest;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Reaction;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.enums.ScheduleStatus;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.ReactionMapper;
import com.vaccine.tracker.repository.ReactionRepository;
import com.vaccine.tracker.service.ChildService;
import com.vaccine.tracker.service.ReactionService;
import com.vaccine.tracker.service.ScheduleService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of ReactionService interface.
 */
@Service
public class ReactionServiceImpl implements ReactionService {
    
    @Autowired
    private ReactionRepository reactionRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ChildService childService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private ReactionMapper reactionMapper;
    
    @Override
    public Reaction findById(Long id) {
        Reaction reaction = reactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reaction not found with id: " + id));
        
        // Check permission
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to access this reaction");
        }
        
        return reaction;
    }
    
    @Override
    @Transactional
    public Reaction create(ReactionRequest reactionRequest) {
        // Validate request
        validateReactionRequest(reactionRequest);
        
        Child child = childService.findById(reactionRequest.getChildId());
        Schedule schedule = scheduleService.findById(reactionRequest.getScheduleId());
        
        // Check if user has permission to report reaction for this child
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && 
            currentUser.getRole() != Role.ROLE_STAFF && 
            !child.getParent().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to report reactions for this child");
        }
        
        // Check if schedule is for this child
        if (!schedule.getChild().getId().equals(child.getId())) {
            throw new BadRequestException("The provided schedule is not for this child");
        }
        
        // Check if the schedule is completed (can only report reactions for completed vaccinations)
        if (schedule.getStatus() != ScheduleStatus.COMPLETED) {
            throw new BadRequestException("You can only report reactions for completed vaccinations");
        }
        
        // Create and save new reaction
        Reaction reaction = reactionMapper.toReaction(reactionRequest, child, schedule);
        
        return reactionRepository.save(reaction);
    }
    
    @Override
    @Transactional
    public Reaction update(Long id, ReactionRequest reactionRequest) {
        // Validate request
        validateReactionRequest(reactionRequest);
        
        Reaction reaction = findById(id);
        
        // Only the reporter, staff, or admin can update reactions
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && 
            currentUser.getRole() != Role.ROLE_STAFF && 
            !reaction.getChild().getParent().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to update this reaction");
        }
        
        // If user is trying to change child or schedule, verify they match the original
        if (!reaction.getChild().getId().equals(reactionRequest.getChildId()) ||
            !reaction.getSchedule().getId().equals(reactionRequest.getScheduleId())) {
            throw new BadRequestException("Cannot change the child or schedule for an existing reaction");
        }
        
        // Update reaction properties
        reactionMapper.updateReactionFromRequest(reaction, reactionRequest);
        
        return reactionRepository.save(reaction);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Reaction reaction = findById(id);
        
        // Only admin can delete reactions
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("Only administrators can delete reactions");
        }
        
        reactionRepository.delete(reaction);
    }
    
    @Override
    @Transactional
    public Reaction addStaffNotes(Long id, String notes) {
        Reaction reaction = findById(id);
        
        // Only staff and admin can add staff notes
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can add notes to reactions");
        }
        
        reaction.setStaffNotes(notes);
        
        return reactionRepository.save(reaction);
    }
    
    @Override
    @Transactional
    public Reaction markResolved(Long id, String notes) {
        Reaction reaction = findById(id);
        
        // Only staff and admin can mark reactions as resolved
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can mark reactions as resolved");
        }
        
        // Mark as resolved with notes
        reaction.markResolved(notes);
        
        return reactionRepository.save(reaction);
    }
    
    @Override
    public List<Reaction> findAll() {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all reactions
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return reactionRepository.findAll();
        } else {
            // Customers can only see their children's reactions
            return reactionRepository.findByParentId(currentUser.getId());
        }
    }
    
    @Override
    public List<Reaction> findByChildId(Long childId) {
        // Check if user has permission to access the child's data
        if (!childService.hasPermission(childId)) {
            throw new UnauthorizedException("You don't have permission to access this child's reactions");
        }
        
        return reactionRepository.findByChildId(childId);
    }
    
    @Override
    public List<Reaction> findByScheduleId(Long scheduleId) {
        // Check if user has permission to access the schedule
        if (!scheduleService.hasPermission(scheduleId)) {
            throw new UnauthorizedException("You don't have permission to access this schedule's reactions");
        }
        
        return reactionRepository.findByScheduleId(scheduleId);
    }
    
    @Override
    public List<Reaction> findByResolved(Boolean resolved) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all resolved/unresolved reactions
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return reactionRepository.findByResolved(resolved);
        } else {
            // Customers can only see their children's reactions
            return reactionRepository.findByResolved(resolved).stream()
                    .filter(reaction -> reaction.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Reaction> findSevereReactions() {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all severe reactions
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return reactionRepository.findSevereReactions();
        } else {
            // Customers can only see their children's severe reactions
            return reactionRepository.findSevereReactions().stream()
                    .filter(reaction -> reaction.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Reaction> findBySeverity(Integer severity) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all reactions by severity
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return reactionRepository.findBySeverity(severity);
        } else {
            // Customers can only see their children's reactions
            return reactionRepository.findBySeverity(severity).stream()
                    .filter(reaction -> reaction.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Reaction> findByReactionDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all reactions in date range
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return reactionRepository.findByReactionDateBetween(startDate, endDate);
        } else {
            // Customers can only see their children's reactions
            return reactionRepository.findByReactionDateBetween(startDate, endDate).stream()
                    .filter(reaction -> reaction.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Reaction> findByVaccineId(Long vaccineId) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all reactions for a vaccine
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return reactionRepository.findByVaccineId(vaccineId);
        } else {
            // Customers can only see their children's reactions
            return reactionRepository.findByVaccineId(vaccineId).stream()
                    .filter(reaction -> reaction.getChild().getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Reaction> findByParentId(Long parentId) {
        User currentUser = userService.getCurrentUser();
        
        // Users can only see their own children's reactions, but admin/staff can see any parent's children's reactions
        if (currentUser.getRole() == Role.ROLE_ADMIN || 
            currentUser.getRole() == Role.ROLE_STAFF || 
            currentUser.getId().equals(parentId)) {
            return reactionRepository.findByParentId(parentId);
        } else {
            throw new UnauthorizedException("You don't have permission to access this parent's reactions");
        }
    }
    
    @Override
    public boolean hasPermission(Long reactionId) {
        User currentUser = userService.getCurrentUser();
        Reaction reaction = reactionRepository.findById(reactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Reaction not found with id: " + reactionId));
        
        // Admin and staff can access any reaction
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return true;
        }
        
        // Parents can only access their children's reactions
        return reaction.getChild().getParent().getId().equals(currentUser.getId());
    }
    
    /**
     * Validate a reaction request.
     * 
     * @param reactionRequest the reaction request to validate
     */
    private void validateReactionRequest(ReactionRequest reactionRequest) {
        if (reactionRequest.getChildId() == null) {
            throw new BadRequestException("Child ID is required");
        }
        
        if (reactionRequest.getScheduleId() == null) {
            throw new BadRequestException("Schedule ID is required");
        }
        
        if (reactionRequest.getSymptoms() == null || reactionRequest.getSymptoms().trim().isEmpty()) {
            throw new BadRequestException("Symptoms are required");
        }
        
        if (reactionRequest.getReactionDate() == null) {
            throw new BadRequestException("Reaction date is required");
        }
        
        if (reactionRequest.getSeverity() == null || reactionRequest.getSeverity() < 1 || reactionRequest.getSeverity() > 5) {
            throw new BadRequestException("Severity must be between 1 and 5");
        }
    }
}
