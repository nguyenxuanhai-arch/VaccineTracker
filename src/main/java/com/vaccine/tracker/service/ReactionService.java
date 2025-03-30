package com.vaccine.tracker.service;

import java.util.List;

import com.vaccine.tracker.entity.Reaction;

public interface ReactionService {
    
    Reaction createReaction(Reaction reaction);
    
    Reaction getReactionById(Long id);
    
    List<Reaction> getAllReactions();
    
    List<Reaction> getReactionsByChildId(Long childId);
    
    List<Reaction> getReactionsByVaccinationId(Long vaccinationId);
    
    List<Reaction> getReactionsByVaccineId(Long vaccineId);
    
    List<Reaction> getReactionsByParentId(Long parentId);
    
    Reaction updateReaction(Long id, Reaction reactionDetails);
    
    void deleteReaction(Long id);
    
    List<Reaction> searchReactions(String keyword);
}