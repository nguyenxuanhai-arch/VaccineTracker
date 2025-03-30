package com.vaccine.tracker.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.vaccine.tracker.dto.ChildDto;
import com.vaccine.tracker.exception.BadRequestException;

@Component
public class ChildValidator {
    
    public void validateChildData(ChildDto childDto) {
        // Check that first name is valid
        if (childDto.getFirstName() == null || childDto.getFirstName().trim().isEmpty()) {
            throw new BadRequestException("First name is required");
        }
        
        // Check that last name is valid
        if (childDto.getLastName() == null || childDto.getLastName().trim().isEmpty()) {
            throw new BadRequestException("Last name is required");
        }
        
        // Check that date of birth is valid
        if (childDto.getDateOfBirth() == null) {
            throw new BadRequestException("Date of birth is required");
        }
        
        // Check that date of birth is in the past
        if (childDto.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new BadRequestException("Date of birth must be in the past");
        }
        
        // Check that gender is valid (if provided)
        if (childDto.getGender() != null && !childDto.getGender().trim().isEmpty()) {
            String gender = childDto.getGender().trim().toUpperCase();
            if (!gender.equals("MALE") && !gender.equals("FEMALE") && !gender.equals("OTHER")) {
                throw new BadRequestException("Gender must be MALE, FEMALE, or OTHER");
            }
        }
    }
}