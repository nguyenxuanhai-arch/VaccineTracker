package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.request.FeedbackRequest;
import com.vaccine.tracker.dto.response.FeedbackResponse;
import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.entity.Feedback;
import com.vaccine.tracker.mapper.FeedbackMapper;
import com.vaccine.tracker.service.FeedbackService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for handling feedback operations.
 */
@Controller
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * Show the feedback form page.
     */
    @GetMapping("/create")
    public String showFeedbackFormPage(
            @RequestParam(required = false) Long scheduleId,
            Model model) {
        
        FeedbackRequest feedbackRequest = new FeedbackRequest();
        if (scheduleId != null) {
            feedbackRequest.setScheduleId(scheduleId);
        }
        
        model.addAttribute("feedbackRequest", feedbackRequest);
        return "feedback/create";
    }

    /**
     * Submit feedback.
     *
     * @param feedbackRequest the feedback details
     * @return the created feedback
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<FeedbackResponse> submitFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.create(feedbackRequest);
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponse(feedback));
    }

    /**
     * Get a specific feedback by ID.
     *
     * @param id the feedback ID
     * @return the feedback details
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<FeedbackResponse> getFeedback(@PathVariable Long id) {
        Feedback feedback = feedbackService.findById(id);
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponse(feedback));
    }

    /**
     * Get all feedback for the current user.
     *
     * @return list of feedback
     */
    @GetMapping("/my-feedback")
    @ResponseBody
    public ResponseEntity<List<FeedbackResponse>> getUserFeedback() {
        Long userId = userService.getCurrentUser().getId();
        List<Feedback> feedbacks = feedbackService.findByUserId(userId);
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponseList(feedbacks));
    }

    /**
     * Get all feedback (admin/staff only).
     *
     * @return list of all feedback
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<FeedbackResponse>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.findAll();
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponseList(feedbacks));
    }

    /**
     * Update a feedback (admin/staff responding to feedback).
     *
     * @param id the feedback ID
     * @param response the staff response
     * @return the updated feedback
     */
    @PostMapping("/{id}/respond")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<FeedbackResponse> respondToFeedback(
            @PathVariable Long id,
            @RequestParam String response) {
        
        Feedback feedback = feedbackService.addResponse(id, response);
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponse(feedback));
    }

    /**
     * Get feedback for a specific schedule.
     *
     * @param scheduleId the schedule ID
     * @return list of feedback
     */
    @GetMapping("/by-schedule/{scheduleId}")
    @ResponseBody
    public ResponseEntity<List<FeedbackResponse>> getFeedbackBySchedule(@PathVariable Long scheduleId) {
        List<Feedback> feedbacks = feedbackService.findByScheduleId(scheduleId);
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponseList(feedbacks));
    }

    /**
     * Get feedback without responses (admin/staff only).
     *
     * @return list of feedback without responses
     */
    @GetMapping("/without-responses")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<FeedbackResponse>> getFeedbackWithoutResponses() {
        List<Feedback> feedbacks = feedbackService.findFeedbackWithoutResponses();
        return ResponseEntity.ok(feedbackMapper.toFeedbackResponseList(feedbacks));
    }

    /**
     * Delete feedback (admin only).
     *
     * @param id the feedback ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> deleteFeedback(@PathVariable Long id) {
        feedbackService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Feedback deleted successfully"));
    }

    /**
     * Get the average rating (admin/staff only).
     *
     * @return the average rating
     */
    @GetMapping("/average-rating")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<Double> getAverageRating() {
        Double averageRating = feedbackService.calculateAverageRating();
        return ResponseEntity.ok(averageRating);
    }
}
