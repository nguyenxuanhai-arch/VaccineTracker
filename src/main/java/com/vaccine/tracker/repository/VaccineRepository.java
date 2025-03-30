package com.vaccine.tracker.repository;

import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Vaccine entities.
 */
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    
    /**
     * Find a vaccine by name.
     * 
     * @param name the vaccine name
     * @return the vaccine if found
     */
    Optional<Vaccine> findByName(String name);
    
    /**
     * Find vaccines by type.
     * 
     * @param type the vaccine type
     * @return list of vaccines of the specified type
     */
    List<Vaccine> findByType(VaccineType type);
    
    /**
     * Find vaccines suitable for children of a specific age in months.
     * 
     * @param ageMonths the age in months
     * @return list of suitable vaccines
     */
    @Query("SELECT v FROM Vaccine v WHERE v.minAgeMonths <= :ageMonths AND (v.maxAgeMonths IS NULL OR v.maxAgeMonths >= :ageMonths)")
    List<Vaccine> findVaccinesSuitableForAge(int ageMonths);
    
    /**
     * Find vaccines by name containing the given keyword.
     * 
     * @param keyword the keyword to search for
     * @return list of vaccines with matching names
     */
    List<Vaccine> findByNameContainingIgnoreCase(String keyword);
    
    /**
     * Find vaccines by manufacturer.
     * 
     * @param manufacturer the manufacturer name
     * @return list of vaccines from the specified manufacturer
     */
    List<Vaccine> findByManufacturerContainingIgnoreCase(String manufacturer);
}
