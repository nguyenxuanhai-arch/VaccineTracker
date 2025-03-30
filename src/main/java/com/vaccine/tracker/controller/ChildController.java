package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.request.ChildRequest;
import com.vaccine.tracker.dto.response.ChildResponse;
import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.User;
import com.vaccine.tracker.mapper.ChildMapper;
import com.vaccine.tracker.service.ChildService;
import com.vaccine.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for handling child operations.
 */
@Controller
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChildMapper childMapper;

    /**
     * Show the list of children page.
     */
    @GetMapping
    public String showChildrenListPage(Model model) {
        User currentUser = userService.getCurrentUser();
        List<Child> children = childService.findByParent(currentUser);
        model.addAttribute("children", childMapper.toChildResponseList(children));
        return "child/list";
    }

    /**
     * Show the create child page.
     */
    @GetMapping("/create")
    public String showCreateChildPage(Model model) {
        model.addAttribute("childRequest", new ChildRequest());
        return "child/create";
    }

    /**
     * Show the edit child page.
     */
    @GetMapping("/{id}/edit")
    public String showEditChildPage(@PathVariable Long id, Model model) {
        Child child = childService.findById(id);
        model.addAttribute("child", childMapper.toChildResponse(child));
        model.addAttribute("childRequest", new ChildRequest());
        return "child/edit";
    }

    /**
     * Get all children for the current user.
     *
     * @return list of children
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<ChildResponse>> getChildren() {
        User currentUser = userService.getCurrentUser();
        List<Child> children = childService.findByParent(currentUser);
        return ResponseEntity.ok(childMapper.toChildResponseList(children));
    }

    /**
     * Get a specific child by ID.
     *
     * @param id the child ID
     * @return the child details
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChildResponse> getChild(@PathVariable Long id) {
        Child child = childService.findById(id);
        return ResponseEntity.ok(childMapper.toChildResponse(child));
    }

    /**
     * Create a new child.
     *
     * @param childRequest the child details
     * @return the created child
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<ChildResponse> createChild(@Valid @RequestBody ChildRequest childRequest) {
        User currentUser = userService.getCurrentUser();
        Child child = childService.create(childRequest, currentUser.getId());
        return ResponseEntity.ok(childMapper.toChildResponse(child));
    }

    /**
     * Update a child's details.
     *
     * @param id the child ID
     * @param childRequest updated child details
     * @return the updated child
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChildResponse> updateChild(
            @PathVariable Long id,
            @Valid @RequestBody ChildRequest childRequest) {
        
        Child child = childService.update(id, childRequest);
        return ResponseEntity.ok(childMapper.toChildResponse(child));
    }

    /**
     * Delete a child.
     *
     * @param id the child ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MessageResponse> deleteChild(@PathVariable Long id) {
        childService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Child deleted successfully"));
    }

    /**
     * Get children by parent ID (admin/staff only).
     *
     * @param parentId the parent user ID
     * @return list of children
     */
    @GetMapping("/by-parent/{parentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<ChildResponse>> getChildrenByParent(@PathVariable Long parentId) {
        List<Child> children = childService.findByParentId(parentId);
        return ResponseEntity.ok(childMapper.toChildResponseList(children));
    }

    /**
     * Search children by name.
     *
     * @param keyword the search keyword
     * @return list of matching children
     */
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<ChildResponse>> searchChildren(@RequestParam String keyword) {
        List<Child> children = childService.findByFullNameContaining(keyword);
        return ResponseEntity.ok(childMapper.toChildResponseList(children));
    }

    /**
     * Find children born between dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of matching children
     */
    @GetMapping("/by-birth-date")
    @ResponseBody
    public ResponseEntity<List<ChildResponse>> getChildrenByBirthDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<Child> children = childService.findByDateOfBirthBetween(startDate, endDate);
        return ResponseEntity.ok(childMapper.toChildResponseList(children));
    }

    /**
     * Find children needing vaccination (admin/staff only).
     *
     * @param maxAgeMonths the maximum age in months
     * @return list of children needing vaccination
     */
    @GetMapping("/needing-vaccination")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @ResponseBody
    public ResponseEntity<List<ChildResponse>> getChildrenNeedingVaccination(
            @RequestParam(defaultValue = "24") int maxAgeMonths) {
        
        List<Child> children = childService.findChildrenNeedingVaccination(maxAgeMonths);
        return ResponseEntity.ok(childMapper.toChildResponseList(children));
    }
}
