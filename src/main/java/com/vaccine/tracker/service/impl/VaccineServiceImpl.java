package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.response.VaccineResponse;
import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.enums.VaccineType;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.VaccineMapper;
import com.vaccine.tracker.repository.VaccineRepository;
import com.vaccine.tracker.service.UserService;
import com.vaccine.tracker.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of VaccineService interface.
 */
@Service
public class VaccineServiceImpl implements VaccineService {
    
    @Autowired
    private VaccineRepository vaccineRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VaccineMapper vaccineMapper;
    
    @Override
    public Vaccine findById(Long id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + id));
    }
    
    @Override
    public Vaccine findByName(String name) {
        return vaccineRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with name: " + name));
    }
    
    @Override
    @Transactional
    public Vaccine create(VaccineResponse vaccineResponse) {
        // Only admin can create vaccines
        if (!userService.getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorizedException("Only administrators can create vaccines");
        }
        
        // Check if vaccine with the same name already exists
        if (vaccineRepository.findByName(vaccineResponse.getName()).isPresent()) {
            throw new BadRequestException("Vaccine with name '" + vaccineResponse.getName() + "' already exists");
        }
        
        // Validate vaccine data
        validateVaccineData(vaccineResponse);
        
        // Create and save new vaccine
        Vaccine vaccine = new Vaccine();
        vaccineMapper.updateVaccineFromResponse(vaccine, vaccineResponse);
        
        return vaccineRepository.save(vaccine);
    }
    
    @Override
    @Transactional
    public Vaccine update(Long id, VaccineResponse vaccineResponse) {
        // Only admin can update vaccines
        if (!userService.getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorizedException("Only administrators can update vaccines");
        }
        
        Vaccine vaccine = findById(id);
        
        // Check if updating to a name that already exists for a different vaccine
        if (!vaccine.getName().equals(vaccineResponse.getName()) &&
                vaccineRepository.findByName(vaccineResponse.getName()).isPresent()) {
            throw new BadRequestException("Vaccine with name '" + vaccineResponse.getName() + "' already exists");
        }
        
        // Validate vaccine data
        validateVaccineData(vaccineResponse);
        
        // Update vaccine properties
        vaccineMapper.updateVaccineFromResponse(vaccine, vaccineResponse);
        
        return vaccineRepository.save(vaccine);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        // Only admin can delete vaccines
        if (!userService.getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorizedException("Only administrators can delete vaccines");
        }
        
        Vaccine vaccine = findById(id);
        
        // Check if vaccine is associated with any schedules
        if (!vaccine.getSchedules().isEmpty()) {
            throw new BadRequestException("Cannot delete vaccine as it is associated with schedules");
        }
        
        vaccineRepository.delete(vaccine);
    }
    
    @Override
    public List<Vaccine> findAll() {
        return vaccineRepository.findAll();
    }
    
    @Override
    public List<Vaccine> findByType(VaccineType type) {
        return vaccineRepository.findByType(type);
    }
    
    @Override
    public List<Vaccine> findVaccinesSuitableForAge(int ageMonths) {
        return vaccineRepository.findVaccinesSuitableForAge(ageMonths);
    }
    
    @Override
    public List<Vaccine> findByNameContaining(String keyword) {
        return vaccineRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    @Override
    public List<Vaccine> findByManufacturer(String manufacturer) {
        return vaccineRepository.findByManufacturerContainingIgnoreCase(manufacturer);
    }
    
    @Override
    @Transactional
    public Vaccine updatePrice(Long id, BigDecimal price) {
        // Only admin can update vaccine prices
        if (!userService.getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new UnauthorizedException("Only administrators can update vaccine prices");
        }
        
        Vaccine vaccine = findById(id);
        
        // Validate price
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Price cannot be negative");
        }
        
        vaccine.setPrice(price);
        
        return vaccineRepository.save(vaccine);
    }
    
    /**
     * Validates vaccine data.
     * 
     * @param vaccineResponse the vaccine data to validate
     */
    private void validateVaccineData(VaccineResponse vaccineResponse) {
        if (vaccineResponse.getName() == null || vaccineResponse.getName().trim().isEmpty()) {
            throw new BadRequestException("Vaccine name cannot be empty");
        }
        
        if (vaccineResponse.getType() == null) {
            throw new BadRequestException("Vaccine type cannot be null");
        }
        
        if (vaccineResponse.getMinAgeMonths() == null || vaccineResponse.getMinAgeMonths() < 0) {
            throw new BadRequestException("Minimum age cannot be negative");
        }
        
        if (vaccineResponse.getMaxAgeMonths() != null && 
                vaccineResponse.getMaxAgeMonths() < vaccineResponse.getMinAgeMonths()) {
            throw new BadRequestException("Maximum age cannot be less than minimum age");
        }
        
        if (vaccineResponse.getPrice() == null || vaccineResponse.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Price cannot be negative");
        }
        
        if (vaccineResponse.getDosesRequired() == null || vaccineResponse.getDosesRequired() <= 0) {
            throw new BadRequestException("Doses required must be at least 1");
        }
    }
}
