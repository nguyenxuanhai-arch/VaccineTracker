package com.vaccine.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.User;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    
    List<Child> findByParent(User parent);
    
    List<Child> findByParentId(Long parentId);
}