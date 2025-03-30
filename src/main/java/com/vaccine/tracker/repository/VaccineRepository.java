package com.vaccine.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Vaccine;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    
    List<Vaccine> findByRecommendedAgeMonthsLessThanEqual(Integer ageInMonths);
    
    @Query("SELECT v FROM Vaccine v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.manufacturer) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Vaccine> searchVaccines(@Param("keyword") String keyword);
}