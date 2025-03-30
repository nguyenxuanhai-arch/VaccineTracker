package com.vaccine.tracker.service.impl;

import com.vaccine.tracker.dto.request.ChildRequest;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.enums.Role;
import com.vaccine.tracker.exception.BadRequestException;
import com.vaccine.tracker.exception.ResourceNotFoundException;
import com.vaccine.tracker.exception.UnauthorizedException;
import com.vaccine.tracker.mapper.ChildMapper;
import com.vaccine.tracker.repository.ChildRepository;
import com.vaccine.tracker.service.ChildService;
import com.vaccine.tracker.service.UserService;
import com.vaccine.tracker.validator.ChildValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of ChildService interface.
 */
@Service
public class ChildServiceImpl implements ChildService {
    
    @Autowired
    private ChildRepository childRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ChildMapper childMapper;
    
    @Autowired
    private ChildValidator childValidator;
    
    @Override
    public Child findById(Long id) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Child not found with id: " + id));
        
        // Check permission
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to access this child's data");
        }
        
        return child;
    }
    
    @Override
    public List<Child> findByParent(User parent) {
        return childRepository.findByParent(parent);
    }
    
    @Override
    public List<Child> findByParentId(Long parentId) {
        User currentUser = userService.getCurrentUser();
        
        // If the current user is staff or admin, or is accessing their own children, allow access
        if (currentUser.getRole() == Role.ROLE_ADMIN || 
            currentUser.getRole() == Role.ROLE_STAFF || 
            currentUser.getId().equals(parentId)) {
            return childRepository.findByParentId(parentId);
        } else {
            throw new UnauthorizedException("You don't have permission to access these children");
        }
    }
    
    @Override
    @Transactional
    public Child create(ChildRequest childRequest, Long parentId) {
        // Validate the request
        childValidator.validate(childRequest);
        
        User parent = userService.findById(parentId);
        
        // Check if current user is authorized to create a child for this parent
        User currentUser = userService.getCurrentUser();
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN) && !currentUser.getId().equals(parentId)) {
            throw new UnauthorizedException("You are not authorized to create a child for this parent");
        }
        
        // Check if a child with the same name and date of birth already exists for this parent
        List<Child> existingChildren = childRepository.findByParentAndFullNameIgnoreCase(parent, childRequest.getFullName());
        for (Child existingChild : existingChildren) {
            if (existingChild.getDateOfBirth().equals(childRequest.getDateOfBirth())) {
                throw new BadRequestException("A child with this name and date of birth already exists for this parent");
            }
        }
        
        // Create and save new child
        Child child = childMapper.toChild(childRequest);
        child.setParent(parent);
        
        return childRepository.save(child);
    }
    
    @Override
    @Transactional
    public Child update(Long id, ChildRequest childRequest) {
        // Validate the request
        childValidator.validate(childRequest);
        
        Child child = findById(id);
        
        // Check if current user has permission to update this child
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to update this child's data");
        }
        
        // Update child properties
        childMapper.updateChildFromRequest(child, childRequest);
        
        return childRepository.save(child);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Child child = findById(id);
        
        // Check if current user has permission to delete this child
        if (!hasPermission(id)) {
            throw new UnauthorizedException("You don't have permission to delete this child");
        }
        
        childRepository.delete(child);
    }
    
    @Override
    public List<Child> findAll() {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can see all children
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return childRepository.findAll();
        } else {
            return findByParent(currentUser);
        }
    }
    
    @Override
    public List<Child> findByFullNameContaining(String keyword) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can search all children
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return childRepository.findByFullNameContainingIgnoreCase(keyword);
        } else {
            // For customers, only search their own children
            List<Child> allMatches = childRepository.findByFullNameContainingIgnoreCase(keyword);
            return allMatches.stream()
                    .filter(child -> child.getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Child> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can search all children
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return childRepository.findByDateOfBirthBetween(startDate, endDate);
        } else {
            // For customers, only search their own children
            List<Child> allMatches = childRepository.findByDateOfBirthBetween(startDate, endDate);
            return allMatches.stream()
                    .filter(child -> child.getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public List<Child> findChildrenNeedingVaccination(int maxAgeMonths) {
        User currentUser = userService.getCurrentUser();
        
        // Only staff and admin can search all children
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return childRepository.findChildrenNeedingVaccination(maxAgeMonths);
        } else {
            // For customers, only search their own children
            List<Child> allMatches = childRepository.findChildrenNeedingVaccination(maxAgeMonths);
            return allMatches.stream()
                    .filter(child -> child.getParent().getId().equals(currentUser.getId()))
                    .toList();
        }
    }
    
    @Override
    public boolean hasPermission(Long childId) {
        User currentUser = userService.getCurrentUser();
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ResourceNotFoundException("Child not found with id: " + childId));
        
        // Admin and staff can access any child
        if (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getRole() == Role.ROLE_STAFF) {
            return true;
        }
        
        // Customers can only access their own children
        return child.getParent().getId().equals(currentUser.getId());
    }
}
