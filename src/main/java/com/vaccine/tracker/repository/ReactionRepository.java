package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Reaction;
import com.vaccine.tracker.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing Reaction entities.
 */
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    
    /**
     * Find reactions by child.
     * 
     * @param child the child
     * @return list of reactions for the child
     */
    List<Reaction> findByChild(Child child);
    
    /**
     * Find reactions by child id.
     * 
     * @param childId the child id
     * @return list of reactions for the child
     */
    List<Reaction> findByChildId(Long childId);
    
    /**
     * Find reactions by schedule.
     * 
     * @param schedule the schedule
     * @return list of reactions for the schedule
     */
    List<Reaction> findBySchedule(Schedule schedule);
    
    /**
     * Find reactions by schedule id.
     * 
     * @param scheduleId the schedule id
     * @return list of reactions for the schedule
     */
    List<Reaction> findByScheduleId(Long scheduleId);
    
    /**
     * Find reactions that have or haven't been resolved.
     * 
     * @param resolved resolution status
     * @return list of reactions with the specified resolution status
     */
    List<Reaction> findByResolved(Boolean resolved);
    
    /**
     * Find severe reactions (severity >= 4).
     * 
     * @return list of severe reactions
     */
    @Query("SELECT r FROM Reaction r WHERE r.severity >= 4")
    List<Reaction> findSevereReactions();
    
    /**
     * Find reactions by severity level.
     * 
     * @param severity the severity level
     * @return list of reactions with the severity
     */
    List<Reaction> findBySeverity(Integer severity);
    
    /**
     * Find reactions that occurred within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of reactions in the date range
     */
    List<Reaction> findByReactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find reactions for a specific vaccine.
     * 
     * @param vaccineId the vaccine id
     * @return list of reactions for the vaccine
     */
    @Query("SELECT r FROM Reaction r JOIN r.schedule s WHERE s.vaccine.id = :vaccineId")
    List<Reaction> findByVaccineId(Long vaccineId);
    
    /**
     * Find reactions for a parent's children.
     * 
     * @param parentId the parent user id
     * @return list of reactions for the parent's children
     */
    @Query("SELECT r FROM Reaction r JOIN r.child c WHERE c.parent.id = :parentId")
    List<Reaction> findByParentId(Long parentId);
}
