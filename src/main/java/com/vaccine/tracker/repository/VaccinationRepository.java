package com.vaccine.tracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Vaccination;
import com.vaccine.tracker.entity.Vaccination.VaccinationStatus;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    
    List<Vaccination> findByChildId(Long childId);
    
    List<Vaccination> findByVaccineId(Long vaccineId);
    
    List<Vaccination> findByProviderId(Long providerId);
    
    List<Vaccination> findByStatus(VaccinationStatus status);
    
    @Query("SELECT v FROM Vaccination v WHERE v.scheduledDate BETWEEN :startDate AND :endDate")
    List<Vaccination> findByScheduledDateBetween(@Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
    
    @Query("SELECT v FROM Vaccination v JOIN v.child c JOIN c.parent p WHERE p.id = :parentId AND v.status = 'SCHEDULED'")
    List<Vaccination> findUpcomingVaccinationsByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT v FROM Vaccination v WHERE v.status = 'SCHEDULED' AND v.scheduledDate < :currentDate")
    List<Vaccination> findOverdueVaccinations(@Param("currentDate") LocalDate currentDate);
}