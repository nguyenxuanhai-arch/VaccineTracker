package com.vaccine.tracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.Schedule.ScheduleStatus;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    List<Schedule> findByChildId(Long childId);
    
    List<Schedule> findByVaccineId(Long vaccineId);
    
    List<Schedule> findByProviderId(Long providerId);
    
    List<Schedule> findByStatus(ScheduleStatus status);
    
    @Query("SELECT s FROM Schedule s WHERE s.scheduledDate BETWEEN :startDate AND :endDate")
    List<Schedule> findByScheduledDateBetween(@Param("startDate") LocalDate startDate, 
                                            @Param("endDate") LocalDate endDate);
    
    @Query("SELECT s FROM Schedule s JOIN s.child c JOIN c.parent p WHERE p.id = :parentId")
    List<Schedule> findSchedulesByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT s FROM Schedule s JOIN s.child c JOIN c.parent p WHERE p.id = :parentId " +
           "AND s.status = 'SCHEDULED' AND s.scheduledDate >= :currentDate")
    List<Schedule> findUpcomingSchedulesByParentId(@Param("parentId") Long parentId, 
                                                @Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT s FROM Schedule s JOIN s.child c JOIN c.parent p WHERE p.id = :parentId " +
           "AND s.status = 'SCHEDULED' AND s.scheduledDate < :currentDate")
    List<Schedule> findOverdueSchedulesByParentId(@Param("parentId") Long parentId, 
                                               @Param("currentDate") LocalDate currentDate);
}