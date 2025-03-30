package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.response.VaccineResponse;
import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.VaccineType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for managing vaccines.
 */
public interface VaccineService {
    
    /**
     * Find vaccine by ID.
     * 
     * @param id the vaccine ID
     * @return the vaccine
     */
    Vaccine findById(Long id);
    
    /**
     * Find vaccine by name.
     * 
     * @param name the vaccine name
     * @return the vaccine
     */
    Vaccine findByName(String name);
    
    /**
     * Create a new vaccine.
     * 
     * @param vaccineResponse the vaccine data
     * @return the created vaccine
     */
    Vaccine create(VaccineResponse vaccineResponse);
    
    /**
     * Update vaccine information.
     * 
     * @param id the vaccine ID
     * @param vaccineResponse the updated vaccine data
     * @return the updated vaccine
     */
    Vaccine update(Long id, VaccineResponse vaccineResponse);
    
    /**
     * Delete vaccine by ID.
     * 
     * @param id the vaccine ID
     */
    void delete(Long id);
    
    /**
     * Find all vaccines.
     * 
     * @return list of all vaccines
     */
    List<Vaccine> findAll();
    
    /**
     * Find vaccines by type.
     * 
     * @param type the vaccine type
     * @return list of vaccines of the type
     */
    List<Vaccine> findByType(VaccineType type);
    
    /**
     * Find vaccines suitable for a specific age in months.
     * 
     * @param ageMonths the age in months
     * @return list of suitable vaccines
     */
    List<Vaccine> findVaccinesSuitableForAge(int ageMonths);
    
    /**
     * Find vaccines by name containing keyword.
     * 
     * @param keyword the search keyword
     * @return list of matching vaccines
     */
    List<Vaccine> findByNameContaining(String keyword);
    
    /**
     * Find vaccines by manufacturer.
     * 
     * @param manufacturer the manufacturer name
     * @return list of vaccines from the manufacturer
     */
    List<Vaccine> findByManufacturer(String manufacturer);
    
    /**
     * Update vaccine price.
     * 
     * @param id the vaccine ID
     * @param price the new price
     * @return the updated vaccine
     */
    Vaccine updatePrice(Long id, BigDecimal price);
}
