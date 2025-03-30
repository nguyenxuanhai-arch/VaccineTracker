package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.request.FeedbackRequest;
import com.vaccine.tracker.entity.Feedback;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing feedback.
 */
public interface FeedbackService {
    
    /**
     * Find feedback by ID.
     * 
     * @param id the feedback ID
     * @return the feedback
     */
    Feedback findById(Long id);
    
    /**
     * Create new feedback.
     * 
     * @param feedbackRequest the feedback details
     * @return the created feedback
     */
    Feedback create(FeedbackRequest feedbackRequest);
    
    /**
     * Delete feedback.
     * 
     * @param id the feedback ID
     */
    void delete(Long id);
    
    /**
     * Add staff response to feedback.
     * 
     * @param id the feedback ID
     * @param response the staff response
     * @return the updated feedback
     */
    Feedback addResponse(Long id, String response);
    
    /**
     * Find all feedback.
     * 
     * @return list of all feedback
     */
    List<Feedback> findAll();
    
    /**
     * Find feedback by user ID.
     * 
     * @param userId the user ID
     * @return list of feedback from the user
     */
    List<Feedback> findByUserId(Long userId);
    
    /**
     * Find feedback by schedule ID.
     * 
     * @param scheduleId the schedule ID
     * @return list of feedback for the schedule
     */
    List<Feedback> findByScheduleId(Long scheduleId);
    
    /**
     * Find feedback created within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of feedback in the date range
     */
    List<Feedback> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find feedback with a specific rating.
     * 
     * @param rating the rating value (1-5)
     * @return list of feedback with the rating
     */
    List<Feedback> findByRating(Integer rating);
    
    /**
     * Calculate the average rating.
     * 
     * @return the average rating
     */
    Double calculateAverageRating();
    
    /**
     * Find feedback that hasn't been responded to.
     * 
     * @return list of feedback without responses
     */
    List<Feedback> findFeedbackWithoutResponses();
    
    /**
     * Check if the current user has permission to access feedback.
     * 
     * @param feedbackId the feedback ID
     * @return true if user has permission
     */
    boolean hasPermission(Long feedbackId);
    
    /**
     * Check if the current user can create feedback for a schedule.
     * 
     * @param scheduleId the schedule ID
     * @return true if user can create feedback
     */
    boolean canCreateFeedbackForSchedule(Long scheduleId);
}
