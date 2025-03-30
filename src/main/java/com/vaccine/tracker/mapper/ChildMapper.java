package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.request.ChildRequest;
import com.vaccine.tracker.dto.response.ChildResponse;
import com.vaccine.tracker.entity.Child;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Child entity and DTOs.
 */
@Component
public class ChildMapper {
    
    /**
     * Converts Child entity to ChildResponse DTO.
     * 
     * @param child the child entity
     * @return the child response
     */
    public ChildResponse toChildResponse(Child child) {
        if (child == null) {
            return null;
        }
        
        ChildResponse response = new ChildResponse();
        response.setId(child.getId());
        response.setFullName(child.getFullName());
        response.setDateOfBirth(child.getDateOfBirth());
        response.setGender(child.getGender());
        response.setMedicalNotes(child.getMedicalNotes());
        response.setAllergies(child.getAllergies());
        
        // Set parent info
        if (child.getParent() != null) {
            response.setParentId(child.getParent().getId());
            response.setParentName(child.getParent().getFullName());
        }
        
        return response;
    }
    
    /**
     * Converts list of Child entities to list of ChildResponse DTOs.
     * 
     * @param children the list of child entities
     * @return the list of child responses
     */
    public List<ChildResponse> toChildResponseList(List<Child> children) {
        if (children == null) {
            return null;
        }
        
        return children.stream()
                .map(this::toChildResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Converts ChildRequest DTO to Child entity.
     * 
     * @param request the child request
     * @return the child entity
     */
    public Child toChild(ChildRequest request) {
        if (request == null) {
            return null;
        }
        
        Child child = new Child();
        child.setFullName(request.getFullName());
        child.setDateOfBirth(request.getDateOfBirth());
        child.setGender(request.getGender());
        child.setMedicalNotes(request.getMedicalNotes());
        child.setAllergies(request.getAllergies());
        
        return child;
    }
    
    /**
     * Updates Child entity from ChildRequest DTO.
     * 
     * @param child the child entity to update
     * @param request the child request with updated data
     */
    public void updateChildFromRequest(Child child, ChildRequest request) {
        if (child == null || request == null) {
            return;
        }
        
        child.setFullName(request.getFullName());
        child.setDateOfBirth(request.getDateOfBirth());
        child.setGender(request.getGender());
        child.setMedicalNotes(request.getMedicalNotes());
        child.setAllergies(request.getAllergies());
    }
}
