package com.vaccine.tracker.service;

import java.util.List;

import com.vaccine.tracker.dto.ChildDto;
import com.vaccine.tracker.entity.Child;

public interface ChildService {
    
    Child createChild(ChildDto childDto);
    
    Child getChildById(Long id);
    
    List<Child> getAllChildren();
    
    List<Child> getChildrenByParentId(Long parentId);
    
    Child updateChild(Long id, ChildDto childDto);
    
    void deleteChild(Long id);
    
    List<Child> searchChildren(String keyword);
}