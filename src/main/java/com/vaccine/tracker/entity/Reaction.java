package com.vaccine.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity representing a child's reaction to a vaccine.
 */
@Entity
@Table(name = "reactions")
public class Reaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
    
    @NotBlank
    @Size(min = 3, max = 1000)
    @Column(name = "symptoms", nullable = false, columnDefinition = "TEXT")
    private String symptoms;
    
    @NotNull
    @Column(name = "reaction_date", nullable = false)
    private LocalDateTime reactionDate;
    
    @Column(name = "severity", nullable = false)
    private Integer severity; // Scale 1-5, 5 being most severe
    
    @Size(max = 1000)
    @Column(name = "treatment", columnDefinition = "TEXT")
    private String treatment;
    
    @Size(max = 1000)
    @Column(name = "staff_notes", columnDefinition = "TEXT")
    private String staffNotes;
    
    @Column(name = "resolved")
    private Boolean resolved = false;
    
    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;
    
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
    public Reaction() {
    }
    
    public Reaction(Child child, Schedule schedule, String symptoms, LocalDateTime reactionDate, Integer severity) {
        this.child = child;
        this.schedule = schedule;
        this.symptoms = symptoms;
        this.reactionDate = reactionDate;
        this.severity = severity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Child getChild() {
        return child;
    }
    
    public void setChild(Child child) {
        this.child = child;
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public String getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public LocalDateTime getReactionDate() {
        return reactionDate;
    }
    
    public void setReactionDate(LocalDateTime reactionDate) {
        this.reactionDate = reactionDate;
    }
    
    public Integer getSeverity() {
        return severity;
    }
    
    public void setSeverity(Integer severity) {
        this.severity = severity;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    public String getStaffNotes() {
        return staffNotes;
    }
    
    public void setStaffNotes(String staffNotes) {
        this.staffNotes = staffNotes;
    }
    
    public Boolean getResolved() {
        return resolved;
    }
    
    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
        if (Boolean.TRUE.equals(resolved)) {
            this.resolvedDate = LocalDateTime.now();
        } else {
            this.resolvedDate = null;
        }
    }
    
    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Mark the reaction as resolved with the given notes.
     * 
     * @param notes staff notes about the resolution
     */
    public void markResolved(String notes) {
        this.resolved = true;
        this.resolvedDate = LocalDateTime.now();
        if (notes != null && !notes.isEmpty()) {
            this.staffNotes = notes;
        }
    }
    
    /**
     * Check if the reaction is severe (4 or 5 on severity scale).
     * 
     * @return true if severe
     */
    public boolean isSevere() {
        return severity != null && severity >= 4;
    }
    
    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", childId=" + (child != null ? child.getId() : null) +
                ", scheduleId=" + (schedule != null ? schedule.getId() : null) +
                ", reactionDate=" + reactionDate +
                ", severity=" + severity +
                ", resolved=" + resolved +
                '}';
    }
}
