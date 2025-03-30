package com.vaccine.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    
    List<Child> findByParentId(Long parentId);
    
    @Query("SELECT c FROM Child c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Child> searchByName(@Param("keyword") String keyword);
}