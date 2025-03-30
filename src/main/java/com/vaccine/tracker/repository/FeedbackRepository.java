package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Feedback;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Feedback entities.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    /**
     * Find feedback by user.
     * 
     * @param user the user
     * @return list of feedback from the user
     */
    List<Feedback> findByUser(User user);
    
    /**
     * Find feedback by user id.
     * 
     * @param userId the user id
     * @return list of feedback from the user
     */
    List<Feedback> findByUserId(Long userId);
    
    /**
     * Find feedback by schedule.
     * 
     * @param schedule the schedule
     * @return list of feedback for the schedule
     */
    List<Feedback> findBySchedule(Schedule schedule);
    
    /**
     * Find feedback by schedule id.
     * 
     * @param scheduleId the schedule id
     * @return list of feedback for the schedule
     */
    List<Feedback> findByScheduleId(Long scheduleId);
    
    /**
     * Find feedback by user and schedule.
     * 
     * @param user the user
     * @param schedule the schedule
     * @return the feedback if found
     */
    Optional<Feedback> findByUserAndSchedule(User user, Schedule schedule);
    
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
    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double calculateAverageRating();
    
    /**
     * Find feedback that hasn't been responded to.
     * 
     * @return list of feedback without responses
     */
    @Query("SELECT f FROM Feedback f WHERE f.staffResponse IS NULL OR f.staffResponse = ''")
    List<Feedback> findFeedbackWithoutResponses();
}
