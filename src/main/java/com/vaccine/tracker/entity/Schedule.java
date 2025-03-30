package com.vaccine.tracker.entity;

import com.vaccine.tracker.enums.ScheduleStatus;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity representing a vaccine schedule in the system.
 */
@Entity
@Table(name = "schedules")
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;
    
    @NotNull
    @Future
    @Column(name = "schedule_date", nullable = false)
    private LocalDateTime scheduleDate;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.PENDING;
    
    @Size(max = 500)
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "dose_number")
    private Integer doseNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Column(name = "completed_date")
    private LocalDateTime completedDate;
    
    @Column(name = "cancellation_reason")
    @Size(max = 500)
    private String cancellationReason;
    
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
    public Schedule() {
    }
    
    public Schedule(Child child, Vaccine vaccine, LocalDateTime scheduleDate) {
        this.child = child;
        this.vaccine = vaccine;
        this.scheduleDate = scheduleDate;
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
    
    public Vaccine getVaccine() {
        return vaccine;
    }
    
    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }
    
    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public ScheduleStatus getStatus() {
        return status;
    }
    
    public void setStatus(ScheduleStatus status) {
        this.status = status;
        
        // If the status is set to COMPLETED, record the completion date
        if (status == ScheduleStatus.COMPLETED) {
            this.completedDate = LocalDateTime.now();
        }
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Integer getDoseNumber() {
        return doseNumber;
    }
    
    public void setDoseNumber(Integer doseNumber) {
        this.doseNumber = doseNumber;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public LocalDateTime getCompletedDate() {
        return completedDate;
    }
    
    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Cancels the schedule with a reason.
     * 
     * @param reason the reason for cancellation
     */
    public void cancel(String reason) {
        this.status = ScheduleStatus.CANCELLED;
        this.cancellationReason = reason;
    }
    
    /**
     * Checks if the schedule can be modified.
     * 
     * @return true if modifiable
     */
    public boolean isModifiable() {
        return status.isModifiable();
    }
    
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", childId=" + (child != null ? child.getId() : null) +
                ", vaccineId=" + (vaccine != null ? vaccine.getId() : null) +
                ", scheduleDate=" + scheduleDate +
                ", status=" + status +
                '}';
    }
}
