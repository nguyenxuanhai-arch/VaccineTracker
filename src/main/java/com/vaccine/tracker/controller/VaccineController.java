package com.vaccine.tracker.controller;

import com.vaccine.tracker.dto.response.MessageResponse;
import com.vaccine.tracker.dto.response.VaccineResponse;
import com.vaccine.tracker.entity.Vaccine;
import com.vaccine.tracker.enums.VaccineType;
import com.vaccine.tracker.mapper.VaccineMapper;
import com.vaccine.tracker.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for handling vaccine operations.
 */
@Controller
@RequestMapping("/api/vaccines")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private VaccineMapper vaccineMapper;

    /**
     * Show the list of vaccines page.
     */
    @GetMapping
    public String showVaccinesListPage(Model model) {
        List<Vaccine> vaccines = vaccineService.findAll();
        model.addAttribute("vaccines", vaccineMapper.toVaccineResponseList(vaccines));
        return "vaccine/list";
    }

    /**
     * Get all vaccines.
     *
     * @return list of all vaccines
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<VaccineResponse>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineService.findAll();
        return ResponseEntity.ok(vaccineMapper.toVaccineResponseList(vaccines));
    }

    /**
     * Get a specific vaccine by ID.
     *
     * @param id the vaccine ID
     * @return the vaccine details
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<VaccineResponse> getVaccine(@PathVariable Long id) {
        Vaccine vaccine = vaccineService.findById(id);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponse(vaccine));
    }

    /**
     * Create a new vaccine (admin only).
     *
     * @param vaccineResponse the vaccine details
     * @return the created vaccine
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<VaccineResponse> createVaccine(@Valid @RequestBody VaccineResponse vaccineResponse) {
        Vaccine vaccine = vaccineService.create(vaccineResponse);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponse(vaccine));
    }

    /**
     * Update a vaccine (admin only).
     *
     * @param id the vaccine ID
     * @param vaccineResponse updated vaccine details
     * @return the updated vaccine
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<VaccineResponse> updateVaccine(
            @PathVariable Long id,
            @Valid @RequestBody VaccineResponse vaccineResponse) {
        
        Vaccine vaccine = vaccineService.update(id, vaccineResponse);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponse(vaccine));
    }

    /**
     * Delete a vaccine (admin only).
     *
     * @param id the vaccine ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<MessageResponse> deleteVaccine(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Vaccine deleted successfully"));
    }

    /**
     * Get vaccines by type.
     *
     * @param type the vaccine type
     * @return list of matching vaccines
     */
    @GetMapping("/by-type")
    @ResponseBody
    public ResponseEntity<List<VaccineResponse>> getVaccinesByType(@RequestParam VaccineType type) {
        List<Vaccine> vaccines = vaccineService.findByType(type);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponseList(vaccines));
    }

    /**
     * Get vaccines suitable for a specific age.
     *
     * @param ageMonths the age in months
     * @return list of suitable vaccines
     */
    @GetMapping("/suitable-for-age")
    @ResponseBody
    public ResponseEntity<List<VaccineResponse>> getVaccinesSuitableForAge(@RequestParam int ageMonths) {
        List<Vaccine> vaccines = vaccineService.findVaccinesSuitableForAge(ageMonths);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponseList(vaccines));
    }

    /**
     * Search vaccines by name.
     *
     * @param keyword the search keyword
     * @return list of matching vaccines
     */
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<VaccineResponse>> searchVaccines(@RequestParam String keyword) {
        List<Vaccine> vaccines = vaccineService.findByNameContaining(keyword);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponseList(vaccines));
    }

    /**
     * Find vaccines by manufacturer.
     *
     * @param manufacturer the manufacturer name
     * @return list of matching vaccines
     */
    @GetMapping("/by-manufacturer")
    @ResponseBody
    public ResponseEntity<List<VaccineResponse>> getVaccinesByManufacturer(@RequestParam String manufacturer) {
        List<Vaccine> vaccines = vaccineService.findByManufacturer(manufacturer);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponseList(vaccines));
    }

    /**
     * Update a vaccine's price (admin only).
     *
     * @param id the vaccine ID
     * @param price the new price
     * @return the updated vaccine
     */
    @PostMapping("/{id}/update-price")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<VaccineResponse> updateVaccinePrice(
            @PathVariable Long id,
            @RequestParam BigDecimal price) {
        
        Vaccine vaccine = vaccineService.updatePrice(id, price);
        return ResponseEntity.ok(vaccineMapper.toVaccineResponse(vaccine));
    }
}
