package com.vaccine.tracker.dto.response;

import com.vaccine.tracker.enums.VaccineType;

import java.math.BigDecimal;

/**
 * DTO for vaccine data responses.
 */
public class VaccineResponse {
    
    private Long id;
    private String name;
    private String description;
    private VaccineType type;
    private Integer minAgeMonths;
    private Integer maxAgeMonths;
    private BigDecimal price;
    private Integer dosesRequired;
    private Integer daysBetweenDoses;
    private String manufacturer;
    private String sideEffects;
    
    // Constructors
    public VaccineResponse() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public VaccineType getType() {
        return type;
    }
    
    public void setType(VaccineType type) {
        this.type = type;
    }
    
    public Integer getMinAgeMonths() {
        return minAgeMonths;
    }
    
    public void setMinAgeMonths(Integer minAgeMonths) {
        this.minAgeMonths = minAgeMonths;
    }
    
    public Integer getMaxAgeMonths() {
        return maxAgeMonths;
    }
    
    public void setMaxAgeMonths(Integer maxAgeMonths) {
        this.maxAgeMonths = maxAgeMonths;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getDosesRequired() {
        return dosesRequired;
    }
    
    public void setDosesRequired(Integer dosesRequired) {
        this.dosesRequired = dosesRequired;
    }
    
    public Integer getDaysBetweenDoses() {
        return daysBetweenDoses;
    }
    
    public void setDaysBetweenDoses(Integer daysBetweenDoses) {
        this.daysBetweenDoses = daysBetweenDoses;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public String getSideEffects() {
        return sideEffects;
    }
    
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
    
    /**
     * Gets a formatted string for the recommended age range.
     * 
     * @return the formatted age range
     */
    public String getAgeRange() {
        if (maxAgeMonths == null) {
            return minAgeMonths + " months and above";
        } else {
            return minAgeMonths + " to " + maxAgeMonths + " months";
        }
    }
    
    /**
     * Gets the type display name.
     * 
     * @return the type display name
     */
    public String getTypeDisplayName() {
        return type != null ? type.getDisplayName() : "";
    }
}
