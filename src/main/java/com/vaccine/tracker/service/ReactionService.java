package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.request.ReactionRequest;
import com.vaccine.tracker.entity.Reaction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing vaccine reactions.
 */
public interface ReactionService {
    
    /**
     * Find reaction by ID.
     * 
     * @param id the reaction ID
     * @return the reaction
     */
    Reaction findById(Long id);
    
    /**
     * Create a new reaction report.
     * 
     * @param reactionRequest the reaction details
     * @return the created reaction
     */
    Reaction create(ReactionRequest reactionRequest);
    
    /**
     * Update a reaction report.
     * 
     * @param id the reaction ID
     * @param reactionRequest updated reaction details
     * @return the updated reaction
     */
    Reaction update(Long id, ReactionRequest reactionRequest);
    
    /**
     * Delete a reaction report.
     * 
     * @param id the reaction ID
     */
    void delete(Long id);
    
    /**
     * Add staff notes to a reaction.
     * 
     * @param id the reaction ID
     * @param notes the staff notes
     * @return the updated reaction
     */
    Reaction addStaffNotes(Long id, String notes);
    
    /**
     * Mark a reaction as resolved.
     * 
     * @param id the reaction ID
     * @param notes resolution notes
     * @return the updated reaction
     */
    Reaction markResolved(Long id, String notes);
    
    /**
     * Find all reactions.
     * 
     * @return list of all reactions
     */
    List<Reaction> findAll();
    
    /**
     * Find reactions by child ID.
     * 
     * @param childId the child ID
     * @return list of reactions for the child
     */
    List<Reaction> findByChildId(Long childId);
    
    /**
     * Find reactions by schedule ID.
     * 
     * @param scheduleId the schedule ID
     * @return list of reactions for the schedule
     */
    List<Reaction> findByScheduleId(Long scheduleId);
    
    /**
     * Find reactions by resolved status.
     * 
     * @param resolved the resolved status
     * @return list of reactions with the status
     */
    List<Reaction> findByResolved(Boolean resolved);
    
    /**
     * Find severe reactions (severity >= 4).
     * 
     * @return list of severe reactions
     */
    List<Reaction> findSevereReactions();
    
    /**
     * Find reactions by severity level.
     * 
     * @param severity the severity level
     * @return list of reactions with the severity
     */
    List<Reaction> findBySeverity(Integer severity);
    
    /**
     * Find reactions within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of reactions in the date range
     */
    List<Reaction> findByReactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find reactions for a specific vaccine.
     * 
     * @param vaccineId the vaccine ID
     * @return list of reactions for the vaccine
     */
    List<Reaction> findByVaccineId(Long vaccineId);
    
    /**
     * Find reactions for a parent's children.
     * 
     * @param parentId the parent user ID
     * @return list of reactions for the parent's children
     */
    List<Reaction> findByParentId(Long parentId);
    
    /**
     * Check if the current user has permission to access a reaction.
     * 
     * @param reactionId the reaction ID
     * @return true if user has permission
     */
    boolean hasPermission(Long reactionId);
}
