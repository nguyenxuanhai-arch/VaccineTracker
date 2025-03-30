package com.vaccine.tracker.service;

import com.vaccine.tracker.dto.request.ChildRequest;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for managing children.
 */
public interface ChildService {
    
    /**
     * Find child by ID.
     * 
     * @param id the child ID
     * @return the child
     */
    Child findById(Long id);
    
    /**
     * Find children by parent.
     * 
     * @param parent the parent user
     * @return list of children
     */
    List<Child> findByParent(User parent);
    
    /**
     * Find children by parent ID.
     * 
     * @param parentId the parent user ID
     * @return list of children
     */
    List<Child> findByParentId(Long parentId);
    
    /**
     * Create a new child.
     * 
     * @param childRequest the child request
     * @param parentId the parent user ID
     * @return the created child
     */
    Child create(ChildRequest childRequest, Long parentId);
    
    /**
     * Update child information.
     * 
     * @param id the child ID
     * @param childRequest the updated child data
     * @return the updated child
     */
    Child update(Long id, ChildRequest childRequest);
    
    /**
     * Delete child by ID.
     * 
     * @param id the child ID
     */
    void delete(Long id);
    
    /**
     * Find all children.
     * 
     * @return list of all children
     */
    List<Child> findAll();
    
    /**
     * Find children by name containing keyword.
     * 
     * @param keyword the search keyword
     * @return list of matching children
     */
    List<Child> findByFullNameContaining(String keyword);
    
    /**
     * Find children born between dates.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of matching children
     */
    List<Child> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find children who need vaccination based on age.
     * 
     * @param maxAgeMonths the maximum age in months
     * @return list of children needing vaccination
     */
    List<Child> findChildrenNeedingVaccination(int maxAgeMonths);
    
    /**
     * Check if the current user has permission to access a child's data.
     * 
     * @param childId the child ID
     * @return true if user has permission
     */
    boolean hasPermission(Long childId);
}
