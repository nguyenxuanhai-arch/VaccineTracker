package com.vaccine.tracker.entity;

import com.vaccine.tracker.enums.VaccineType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a vaccine in the system.
 */
@Entity
@Table(name = "vaccines")
public class Vaccine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, unique = true)
    private String name;
    
    @Size(max = 500)
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VaccineType type;
    
    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer minAgeMonths;
    
    @Min(0)
    @Column(name = "max_age_months")
    private Integer maxAgeMonths;
    
    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private BigDecimal price;
    
    @Min(0)
    @Column(name = "doses_required", nullable = false)
    private Integer dosesRequired = 1;
    
    @Min(0)
    @Column(name = "days_between_doses")
    private Integer daysBetweenDoses;
    
    @Size(max = 200)
    @Column(name = "manufacturer")
    private String manufacturer;
    
    @Size(max = 500)
    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects;
    
    @OneToMany(mappedBy = "vaccine")
    private Set<Schedule> schedules = new HashSet<>();
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Vaccine() {
    }
    
    public Vaccine(String name, String description, VaccineType type, Integer minAgeMonths, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.minAgeMonths = minAgeMonths;
        this.price = price;
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
    
    public Set<Schedule> getSchedules() {
        return schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Checks if the vaccine is suitable for a child of the given age in months.
     * 
     * @param ageMonths the age in months
     * @return true if suitable
     */
    public boolean isSuitableForAge(int ageMonths) {
        boolean olderThanMinAge = ageMonths >= minAgeMonths;
        boolean youngerThanMaxAge = maxAgeMonths == null || ageMonths <= maxAgeMonths;
        return olderThanMinAge && youngerThanMaxAge;
    }
    
    @Override
    public String toString() {
        return "Vaccine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", minAgeMonths=" + minAgeMonths +
                ", price=" + price +
                '}';
    }
}
