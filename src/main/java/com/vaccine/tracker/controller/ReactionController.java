package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.request.ReactionRequest;
import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.dto.response.ReactionResponse;
import com.vaccine.tracker.entity.Reaction;
import com.vaccine.tracker.mapper.ReactionMapper;
import com.vaccine.tracker.service.ReactionService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for handling vaccine reaction reports.
 */
@Controller
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReactionMapper reactionMapper;

    /**
     * Report a vaccine reaction.
     *
     * @param reactionRequest the reaction details
     * @return the created reaction report
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<ReactionResponse> reportReaction(@Valid @RequestBody ReactionRequest reactionRequest) {
        Reaction reaction = reactionService.create(reactionRequest);
        return ResponseEntity.ok(reactionMapper.toReactionResponse(reaction));
    }

    /**
     * Get a specific reaction by ID.
     *
     * @param id the reaction ID
     * @return the reaction details
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ReactionResponse> getReaction(@PathVariable Long id) {
        Reaction reaction = reactionService.findById(id);
        return ResponseEntity.ok(reactionMapper.toReactionResponse(reaction));
    }

    /**
     * Update a reaction report.
     *
     * @param id the reaction ID
     * @param reactionRequest updated reaction details
     * @return the updated reaction
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ReactionResponse> updateReaction(
            @PathVariable Long id,
            @Valid @RequestBody ReactionRequest reactionRequest) {
        
        Reaction reaction = reactionService.update(id, reactionRequest);
        return ResponseEntity.ok(reactionMapper.toReactionResponse(reaction));
    }

    /**
     * Get reactions for a specific child.
     *
     * @param childId the child ID
     * @return list of reactions
     */
    @GetMapping("/child/{childId}")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getReactionsByChild(@PathVariable Long childId) {
        List<Reaction> reactions = reactionService.findByChildId(childId);
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Get reactions for all children of the current user.
     *
     * @return list of reactions
     */
    @GetMapping("/my-children")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getReactionsForUserChildren() {
        Long parentId = userService.getCurrentUser().getId();
        List<Reaction> reactions = reactionService.findByParentId(parentId);
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Get reactions for a specific schedule.
     *
     * @param scheduleId the schedule ID
     * @return list of reactions
     */
    @GetMapping("/schedule/{scheduleId}")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getReactionsBySchedule(@PathVariable Long scheduleId) {
        List<Reaction> reactions = reactionService.findByScheduleId(scheduleId);
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Get all reactions (admin/staff only).
     *
     * @return list of all reactions
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getAllReactions() {
        List<Reaction> reactions = reactionService.findAll();
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Add staff notes to a reaction (staff/admin only).
     *
     * @param id the reaction ID
     * @param notes the staff notes
     * @return the updated reaction
     */
    @PostMapping("/{id}/notes")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<ReactionResponse> addStaffNotes(
            @PathVariable Long id,
            @RequestParam String notes) {
        
        Reaction reaction = reactionService.addStaffNotes(id, notes);
        return ResponseEntity.ok(reactionMapper.toReactionResponse(reaction));
    }

    /**
     * Mark a reaction as resolved (staff/admin only).
     *
     * @param id the reaction ID
     * @param notes resolution notes
     * @return the updated reaction
     */
    @PostMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<ReactionResponse> resolveReaction(
            @PathVariable Long id,
            @RequestParam(required = false) String notes) {
        
        Reaction reaction = reactionService.markResolved(id, notes);
        return ResponseEntity.ok(reactionMapper.toReactionResponse(reaction));
    }

    /**
     * Get severe reactions (severity >= 4) (staff/admin only).
     *
     * @return list of severe reactions
     */
    @GetMapping("/severe")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getSevereReactions() {
        List<Reaction> reactions = reactionService.findSevereReactions();
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Get unresolved reactions (staff/admin only).
     *
     * @return list of unresolved reactions
     */
    @GetMapping("/unresolved")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getUnresolvedReactions() {
        List<Reaction> reactions = reactionService.findByResolved(false);
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Get reactions by date range (staff/admin only).
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of reactions in the date range
     */
    @GetMapping("/by-date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<ReactionResponse>> getReactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        
        List<Reaction> reactions = reactionService.findByReactionDateBetween(startDate, endDate);
        return ResponseEntity.ok(reactionMapper.toReactionResponseList(reactions));
    }

    /**
     * Delete a reaction (admin only).
     *
     * @param id the reaction ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> deleteReaction(@PathVariable Long id) {
        reactionService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Reaction deleted successfully"));
    }
}
