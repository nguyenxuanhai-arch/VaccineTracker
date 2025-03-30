package com.vaccine.tracker.service;

import java.util.List;

import com.vaccine.tracker.entity.Vaccine;

public interface VaccineService {
    
    Vaccine createVaccine(Vaccine vaccine);
    
    Vaccine getVaccineById(Long id);
    
    List<Vaccine> getAllVaccines();
    
    Vaccine updateVaccine(Long id, Vaccine vaccineDetails);
    
    void deleteVaccine(Long id);
    
    List<Vaccine> searchVaccines(String keyword);
    
    List<Vaccine> getRecommendedVaccinesForAge(int ageInMonths);
}