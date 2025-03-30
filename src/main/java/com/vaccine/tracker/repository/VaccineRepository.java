package com.vaccine.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Vaccine;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    
    Optional<Vaccine> findByName(String name);
    
    boolean existsByName(String name);
}