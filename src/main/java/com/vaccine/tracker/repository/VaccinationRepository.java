package com.vaccine.tracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.entity.Vaccination;
import com.vaccine.tracker.entity.Vaccination.VaccinationStatus;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    
    List<Vaccination> findByChild(Child child);
    
    List<Vaccination> findByChildId(Long childId);
    
    List<Vaccination> findByProvider(User provider);
    
    List<Vaccination> findByProviderId(Long providerId);
    
    List<Vaccination> findByStatus(VaccinationStatus status);
    
    List<Vaccination> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT v FROM Vaccination v WHERE v.child.parent.id = :parentId")
    List<Vaccination> findAllByParentId(Long parentId);
    
    @Query("SELECT v FROM Vaccination v WHERE v.child.id = :childId AND v.scheduledDate BETWEEN :startDate AND :endDate")
    List<Vaccination> findByChildIdAndDateRange(Long childId, LocalDate startDate, LocalDate endDate);
}