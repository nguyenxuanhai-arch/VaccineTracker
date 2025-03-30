package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.request.FeedbackRequest;
import com.vaccine.tracker.entity.Feedback;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.enums.ScheduleStatus;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.FeedbackMapper;
import com.vaccine.tracker.repository.FeedbackRepository;
import com.vaccine.tracker.service.FeedbackService;
import com.vaccine.tracker.service.ScheduleService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of FeedbackService interface.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private FeedbackMapper feedbackMapper;
    
    @Override
    public Feedback findById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + id));
        
        // Check permission
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to access this feedback");
        }
        
        return feedback;
    }
    
    @Override
    @Transactional
    public Feedback create(FeedbackRequest feedbackRequest) {
        User currentUser = userService.getCurrentUser();
        
        // If schedule ID is provided, check permission
        Schedule schedule = null;
        if (feedbackRequest.getScheduleId() != null) {
            if (!canCreateFeedbackForSchedule(feedbackRequest.getScheduleId())) {
                throw new UnauthorizedException("You can't create feedback for this schedule");
            }
            
            schedule = scheduleService.findById(feedbackRequest.getScheduleId());
            
            // Check if the schedule is completed
            if (schedule.getStatus() != ScheduleStatus.COMPLETED) {
                throw new BadRequestException("You can only provide feedback for completed vaccinations");
            }
            
            // Check if user already provided feedback for this schedule
            Optional<Feedback> existingFeedback = feedbackRepository.findByUserAndSchedule(currentUser, schedule);
            if (existingFeedback.isPresent()) {
                throw new BadRequestException("You have already provided feedback for this schedule");
            }
        }
        
        // Create and save new feedback
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequest, currentUser, schedule);
        
        return feedbackRepository.save(feedback);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Feedback feedback = findById(id);
        
        // Only admin or the feedback author can delete
        User currentUser = userService.getCurrentUser();
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN) && !feedback.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You don't have permission to delete this feedback");
        }
        
        feedbackRepository.delete(feedback);
    }
    
    @Override
    @Transactional
    public Feedback addResponse(Long id, String response) {
        Feedback feedback = findById(id);
        
        // Only staff and admin can respond to feedback
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can respond to feedback");
        }
        
        // Add response
        feedback.setStaffResponse(response);
        feedback.setRespondedAt(LocalDateTime.now());
        
        return feedbackRepository.save(feedback);
    }
    
    @Override
    public List<Feedback> findAll() {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all feedback
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return feedbackRepository.findAll();
        } else {
            // Customers can only see their own feedback
            return feedbackRepository.findByUser(currentUser);
        }
    }
    
    @Override
    public List<Feedback> findByUserId(Long userId) {
        User currentUser = userService.getCurrentUser();
        
        // Users can only see their own feedback, but admin/staff can see any user's feedback
        if (currentUser.getRole() == Role.ROLE_ADMIN || 
            currentUser.getRole() == Role.ROLE_STAFF || 
            currentUser.getId().equals(userId)) {
            return feedbackRepository.findByUserId(userId);
        } else {
            throw new UnauthorizedException("You don't have permission to access this user's feedback");
        }
    }
    
    @Override
    public List<Feedback> findByScheduleId(Long scheduleId) {
        // Check if user has permission to access the schedule
        if (!scheduleService.hasPermission(scheduleId)) {
            throw new UnauthorizedException("You don't have permission to access this schedule's feedback");
        }
        
        return feedbackRepository.findByScheduleId(scheduleId);
    }
    
    @Override
    public List<Feedback> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can search all feedback
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return feedbackRepository.findByCreatedAtBetween(startDate, endDate);
        } else {
            // For customers, only show their own feedback
            return feedbackRepository.findByCreatedAtBetween(startDate, endDate).stream()
                    .filter(feedback -> feedback.getUser().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Feedback> findByRating(Integer rating) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can search all feedback
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return feedbackRepository.findByRating(rating);
        } else {
            // For customers, only show their own feedback
            return feedbackRepository.findByRating(rating).stream()
                    .filter(feedback -> feedback.getUser().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public Double calculateAverageRating() {
        return feedbackRepository.calculateAverageRating();
    }
    
    @Override
    public List<Feedback> findFeedbackWithoutResponses() {
        // Only staff and admin can see feedback without responses
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() != Role.ROLE_ADMIN && currentUser.getRole() != Role.ROLE_STAFF) {
            throw new UnauthorizedException("Only staff can access this information");
        }
        
        return feedbackRepository.findFeedbackWithoutResponses();
    }
    
    @Override
    public boolean hasPermission(Long feedbackId) {
        User currentUser = userService.getCurrentUser();
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));
        
        // Admin and staff can access any feedback
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return true;
        }
        
        // Users can only access their own feedback
        return feedback.getUser().getId().equals(currentUser.getId());
    }
    
    @Override
    public boolean canCreateFeedbackForSchedule(Long scheduleId) {
        User currentUser = userService.getCurrentUser();
        Schedule schedule = scheduleService.findById(scheduleId);
        
        // Check if the schedule belongs to one of the user's children
        return schedule.getChild().getParent().getId().equals(currentUser.getId());
    }
}
