package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.response.VaccineResponse;
import com.vaccine.tracker.entity.Vaccine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Vaccine entity and DTOs.
 */
@Component
public class VaccineMapper {
    
    /**
     * Converts Vaccine entity to VaccineResponse DTO.
     * 
     * @param vaccine the vaccine entity
     * @return the vaccine response
     */
    public VaccineResponse toVaccineResponse(Vaccine vaccine) {
        if (vaccine == null) {
            return null;
        }
        
        VaccineResponse response = new VaccineResponse();
        response.setId(vaccine.getId());
        response.setName(vaccine.getName());
        response.setDescription(vaccine.getDescription());
        response.setType(vaccine.getType());
        response.setMinAgeMonths(vaccine.getMinAgeMonths());
        response.setMaxAgeMonths(vaccine.getMaxAgeMonths());
        response.setPrice(vaccine.getPrice());
        response.setDosesRequired(vaccine.getDosesRequired());
        response.setDaysBetweenDoses(vaccine.getDaysBetweenDoses());
        response.setManufacturer(vaccine.getManufacturer());
        response.setSideEffects(vaccine.getSideEffects());
        
        return response;
    }
    
    /**
     * Converts list of Vaccine entities to list of VaccineResponse DTOs.
     * 
     * @param vaccines the list of vaccine entities
     * @return the list of vaccine responses
     */
    public List<VaccineResponse> toVaccineResponseList(List<Vaccine> vaccines) {
        if (vaccines == null) {
            return null;
        }
        
        return vaccines.stream()
                .map(this::toVaccineResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Updates Vaccine entity from VaccineResponse DTO.
     * 
     * @param vaccine the vaccine entity to update
     * @param response the vaccine response with updated data
     */
    public void updateVaccineFromResponse(Vaccine vaccine, VaccineResponse response) {
        if (vaccine == null || response == null) {
            return;
        }
        
        vaccine.setName(response.getName());
        vaccine.setDescription(response.getDescription());
        vaccine.setType(response.getType());
        vaccine.setMinAgeMonths(response.getMinAgeMonths());
        vaccine.setMaxAgeMonths(response.getMaxAgeMonths());
        vaccine.setPrice(response.getPrice());
        vaccine.setDosesRequired(response.getDosesRequired());
        vaccine.setDaysBetweenDoses(response.getDaysBetweenDoses());
        vaccine.setManufacturer(response.getManufacturer());
        vaccine.setSideEffects(response.getSideEffects());
    }
}
