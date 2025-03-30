package com.vaccine.tracker.entity;

import com.vaccine.tracker.enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a child in the system.
 */
@Entity
@Table(name = "children")
public class Child {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Past
    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @Size(max = 200)
    @Column(name = "medical_notes")
    private String medicalNotes;
    
    @Size(max = 200)
    @Column(name = "allergies")
    private String allergies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private User parent;
    
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Schedule> schedules = new HashSet<>();
    
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reaction> reactions = new HashSet<>();
    
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
    public Child() {
    }
    
    public Child(String fullName, LocalDate dateOfBirth, Gender gender, User parent) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.parent = parent;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getMedicalNotes() {
        return medicalNotes;
    }
    
    public void setMedicalNotes(String medicalNotes) {
        this.medicalNotes = medicalNotes;
    }
    
    public String getAllergies() {
        return allergies;
    }
    
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
    
    public User getParent() {
        return parent;
    }
    
    public void setParent(User parent) {
        this.parent = parent;
    }
    
    public Set<Schedule> getSchedules() {
        return schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    
    public Set<Reaction> getReactions() {
        return reactions;
    }
    
    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Adds a schedule to this child's schedules.
     * 
     * @param schedule the schedule to add
     */
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setChild(this);
    }
    
    /**
     * Removes a schedule from this child's schedules.
     * 
     * @param schedule the schedule to remove
     */
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setChild(null);
    }
    
    /**
     * Adds a reaction to this child's reactions.
     * 
     * @param reaction the reaction to add
     */
    public void addReaction(Reaction reaction) {
        reactions.add(reaction);
        reaction.setChild(this);
    }
    
    /**
     * Removes a reaction from this child's reactions.
     * 
     * @param reaction the reaction to remove
     */
    public void removeReaction(Reaction reaction) {
        reactions.remove(reaction);
        reaction.setChild(null);
    }
    
    /**
     * Calculates the age of the child in years.
     * 
     * @return the age in years
     */
    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }
}
