package com.vaccine.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    
    List<Reaction> findByChildId(Long childId);
    
    List<Reaction> findByVaccinationId(Long vaccinationId);
    
    @Query("SELECT r FROM Reaction r JOIN r.vaccination v WHERE v.vaccine.id = :vaccineId")
    List<Reaction> findByVaccineId(@Param("vaccineId") Long vaccineId);
    
    @Query("SELECT r FROM Reaction r JOIN r.child c JOIN c.parent p WHERE p.id = :parentId")
    List<Reaction> findByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT r FROM Reaction r WHERE LOWER(r.symptom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Reaction> searchReactions(@Param("keyword") String keyword);
}