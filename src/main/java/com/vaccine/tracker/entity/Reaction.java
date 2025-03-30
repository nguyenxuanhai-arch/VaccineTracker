package com.vaccine.tracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccination_id")
    private Vaccination vaccination;

    @NotNull
    private LocalDateTime reactionDate;

    @NotBlank
    private String symptom;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Column(length = 1000)
    private String description;

    private String treatmentProvided;

    private Boolean reportedToProvider;

    private LocalDateTime resolvedDate;

    // Enum for reaction severity
    public enum Severity {
        MILD,
        MODERATE,
        SEVERE,
        LIFE_THREATENING
    }
}