package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.request.ReactionRequest;
import com.vaccine.tracker.dto.response.ReactionResponse;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Reaction;
import com.vaccine.tracker.entity.Schedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Reaction entity and DTOs.
 */
@Component
public class ReactionMapper {
    
    /**
     * Converts Reaction entity to ReactionResponse DTO.
     * 
     * @param reaction the reaction entity
     * @return the reaction response
     */
    public ReactionResponse toReactionResponse(Reaction reaction) {
        if (reaction == null) {
            return null;
        }
        
        ReactionResponse response = new ReactionResponse();
        response.setId(reaction.getId());
        response.setSymptoms(reaction.getSymptoms());
        response.setReactionDate(reaction.getReactionDate());
        response.setSeverity(reaction.getSeverity());
        response.setTreatment(reaction.getTreatment());
        response.setStaffNotes(reaction.getStaffNotes());
        response.setResolved(reaction.getResolved());
        response.setResolvedDate(reaction.getResolvedDate());
        
        // Set child info
        if (reaction.getChild() != null) {
            response.setChildId(reaction.getChild().getId());
            response.setChildName(reaction.getChild().getFullName());
        }
        
        // Set schedule and vaccine info
        if (reaction.getSchedule() != null) {
            response.setScheduleId(reaction.getSchedule().getId());
            
            if (reaction.getSchedule().getVaccine() != null) {
                response.setVaccineName(reaction.getSchedule().getVaccine().getName());
            }
        }
        
        return response;
    }
    
    /**
     * Converts list of Reaction entities to list of ReactionResponse DTOs.
     * 
     * @param reactions the list of reaction entities
     * @return the list of reaction responses
     */
    public List<ReactionResponse> toReactionResponseList(List<Reaction> reactions) {
        if (reactions == null) {
            return null;
        }
        
        return reactions.stream()
                .map(this::toReactionResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a new Reaction entity from ReactionRequest DTO.
     * 
     * @param request the reaction request
     * @param child the child entity
     * @param schedule the schedule entity
     * @return the reaction entity
     */
    public Reaction toReaction(ReactionRequest request, Child child, Schedule schedule) {
        if (request == null) {
            return null;
        }
        
        Reaction reaction = new Reaction();
        reaction.setChild(child);
        reaction.setSchedule(schedule);
        reaction.setSymptoms(request.getSymptoms());
        reaction.setReactionDate(request.getReactionDate());
        reaction.setSeverity(request.getSeverity());
        reaction.setTreatment(request.getTreatment());
        reaction.setResolved(false);
        
        return reaction;
    }
    
    /**
     * Updates Reaction entity from ReactionRequest DTO.
     * 
     * @param reaction the reaction entity to update
     * @param request the reaction request with updated data
     */
    public void updateReactionFromRequest(Reaction reaction, ReactionRequest request) {
        if (reaction == null || request == null) {
            return;
        }
        
        reaction.setSymptoms(request.getSymptoms());
        reaction.setReactionDate(request.getReactionDate());
        reaction.setSeverity(request.getSeverity());
        reaction.setTreatment(request.getTreatment());
    }
}
