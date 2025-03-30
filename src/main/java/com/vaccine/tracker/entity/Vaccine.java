package com.vaccine.tracker.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vaccines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(length = 1000)
    private String description;

    @NotBlank
    private String manufacturer;

    @NotNull
    private Integer recommendedAgeMonths;

    private Integer doseCount;

    private Integer daysBetweenDoses;

    @Column(length = 1000)
    private String sideEffects;

    @OneToMany(mappedBy = "vaccine", cascade = CascadeType.ALL)
    private List<Vaccination> vaccinations = new ArrayList<>();
}