package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.request.FeedbackRequest;
import com.vaccine.tracker.dto.response.FeedbackResponse;
import com.vaccine.tracker.entity.Feedback;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Feedback entity and DTOs.
 */
@Component
public class FeedbackMapper {
    
    /**
     * Converts Feedback entity to FeedbackResponse DTO.
     * 
     * @param feedback the feedback entity
     * @return the feedback response
     */
    public FeedbackResponse toFeedbackResponse(Feedback feedback) {
        if (feedback == null) {
            return null;
        }
        
        FeedbackResponse response = new FeedbackResponse();
        response.setId(feedback.getId());
        response.setRating(feedback.getRating());
        response.setComment(feedback.getComment());
        response.setStaffResponse(feedback.getStaffResponse());
        response.setRespondedAt(feedback.getRespondedAt());
        response.setCreatedAt(feedback.getCreatedAt());
        
        // Set user info
        if (feedback.getUser() != null) {
            response.setUserId(feedback.getUser().getId());
            response.setUserName(feedback.getUser().getFullName());
        }
        
        // Set schedule and vaccine info
        if (feedback.getSchedule() != null) {
            response.setScheduleId(feedback.getSchedule().getId());
            
            if (feedback.getSchedule().getVaccine() != null) {
                response.setVaccineName(feedback.getSchedule().getVaccine().getName());
            }
        }
        
        return response;
    }
    
    /**
     * Converts list of Feedback entities to list of FeedbackResponse DTOs.
     * 
     * @param feedbacks the list of feedback entities
     * @return the list of feedback responses
     */
    public List<FeedbackResponse> toFeedbackResponseList(List<Feedback> feedbacks) {
        if (feedbacks == null) {
            return null;
        }
        
        return feedbacks.stream()
                .map(this::toFeedbackResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a new Feedback entity from FeedbackRequest DTO.
     * 
     * @param request the feedback request
     * @param user the user entity
     * @param schedule the schedule entity
     * @return the feedback entity
     */
    public Feedback toFeedback(FeedbackRequest request, User user, Schedule schedule) {
        if (request == null) {
            return null;
        }
        
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
        feedback.setSchedule(schedule);
        
        return feedback;
    }
    
    /**
     * Updates Feedback entity from FeedbackRequest DTO.
     * 
     * @param feedback the feedback entity to update
     * @param request the feedback request with updated data
     */
    public void updateFeedbackFromRequest(Feedback feedback, FeedbackRequest request) {
        if (feedback == null || request == null) {
            return;
        }
        
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
    }
}
