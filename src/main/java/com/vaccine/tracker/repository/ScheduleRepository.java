package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing Schedule entities.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    /**
     * Find schedules by child.
     * 
     * @param child the child
     * @return list of schedules for the child
     */
    List<Schedule> findByChild(Child child);
    
    /**
     * Find schedules by child id.
     * 
     * @param childId the child id
     * @return list of schedules for the child
     */
    List<Schedule> findByChildId(Long childId);
    
    /**
     * Find schedules by vaccine.
     * 
     * @param vaccine the vaccine
     * @return list of schedules for the vaccine
     */
    List<Schedule> findByVaccine(Vaccine vaccine);
    
    /**
     * Find schedules by vaccine id.
     * 
     * @param vaccineId the vaccine id
     * @return list of schedules for the vaccine
     */
    List<Schedule> findByVaccineId(Long vaccineId);
    
    /**
     * Find schedules by status.
     * 
     * @param status the schedule status
     * @return list of schedules with the specified status
     */
    List<Schedule> findByStatus(ScheduleStatus status);
    
    /**
     * Find upcoming schedules for a child.
     * 
     * @param child the child
     * @param currentDate the current date
     * @return list of upcoming schedules
     */
    List<Schedule> findByChildAndScheduleDateAfterAndStatusNot(
            Child child, 
            LocalDateTime currentDate, 
            ScheduleStatus cancelledStatus);
    
    /**
     * Find schedules within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of schedules in the date range
     */
    List<Schedule> findByScheduleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find schedules within a date range with a specific status.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @param status the schedule status
     * @return list of matching schedules
     */
    List<Schedule> findByScheduleDateBetweenAndStatus(
            LocalDateTime startDate, 
            LocalDateTime endDate, 
            ScheduleStatus status);
    
    /**
     * Find schedules for a parent user through their children.
     * 
     * @param parentId the parent user id
     * @return list of schedules for the parent's children
     */
    @Query("SELECT s FROM Schedule s JOIN s.child c WHERE c.parent.id = :parentId")
    List<Schedule> findByParentId(Long parentId);
    
    /**
     * Count schedules by status.
     * 
     * @param status the schedule status
     * @return count of schedules with the status
     */
    long countByStatus(ScheduleStatus status);
    
    /**
     * Count schedules by status and date range.
     * 
     * @param status the schedule status
     * @param startDate the start date
     * @param endDate the end date
     * @return count of matching schedules
     */
    long countByStatusAndScheduleDateBetween(
            ScheduleStatus status, 
            LocalDateTime startDate, 
            LocalDateTime endDate);
}
