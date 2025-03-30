package com.vaccine.tracker.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;

    @NotNull
    private LocalDate scheduledDate;

    private LocalDate administeredDate;

    private Integer doseNumber;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administered_by")
    private User provider;

    private String notes;

    // Enum for schedule status
    public enum ScheduleStatus {
        SCHEDULED,
        COMPLETED,
        MISSED,
        POSTPONED
    }

    // Helper methods
    public boolean isComplete() {
        return this.status == ScheduleStatus.COMPLETED && this.administeredDate != null;
    }

    public boolean isOverdue() {
        return this.status == ScheduleStatus.SCHEDULED && 
               this.scheduledDate.isBefore(LocalDate.now());
    }

    public boolean isUpcoming() {
        return this.status == ScheduleStatus.SCHEDULED && 
               (this.scheduledDate.isEqual(LocalDate.now()) || 
                this.scheduledDate.isAfter(LocalDate.now()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, child, vaccine, doseNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Schedule schedule = (Schedule) obj;
        
        // For new objects without IDs yet, compare by child, vaccine and dose number
        if (id == null || schedule.id == null) {
            return Objects.equals(child.getId(), schedule.child.getId()) &&
                   Objects.equals(vaccine.getId(), schedule.vaccine.getId()) &&
                   Objects.equals(doseNumber, schedule.doseNumber);
        }
        
        return Objects.equals(id, schedule.id);
    }
}