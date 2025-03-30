package com.vaccine.tracker.mapper;

import com.vaccine.tracker.dto.request.ScheduleRequest;
import com.vaccine.tracker.dto.response.ScheduleResponse;
import com.vaccine.tracker.entity.Child;
import com.vaccine.tracker.entity.Schedule;
import com.vaccine.tracker.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Schedule entity and DTOs.
 */
@Component
public class ScheduleMapper {
    
    @Autowired
    private ChildMapper childMapper;
    
    @Autowired
    private VaccineMapper vaccineMapper;
    
    /**
     * Converts Schedule entity to ScheduleResponse DTO.
     * 
     * @param schedule the schedule entity
     * @return the schedule response
     */
    public ScheduleResponse toScheduleResponse(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        
        ScheduleResponse response = new ScheduleResponse();
        response.setId(schedule.getId());
        response.setScheduleDate(schedule.getScheduleDate());
        response.setStatus(schedule.getStatus());
        response.setNotes(schedule.getNotes());
        response.setDoseNumber(schedule.getDoseNumber());
        response.setCompletedDate(schedule.getCompletedDate());
        response.setCancellationReason(schedule.getCancellationReason());
        response.setCanModify(schedule.isModifiable());
        
        // Set child and vaccine info
        if (schedule.getChild() != null) {
            response.setChild(childMapper.toChildResponse(schedule.getChild()));
            response.setChildId(schedule.getChild().getId());
            response.setChildName(schedule.getChild().getFullName());
        }
        
        if (schedule.getVaccine() != null) {
            response.setVaccine(vaccineMapper.toVaccineResponse(schedule.getVaccine()));
            response.setVaccineId(schedule.getVaccine().getId());
            response.setVaccineName(schedule.getVaccine().getName());
        }
        
        return response;
    }
    
    /**
     * Converts list of Schedule entities to list of ScheduleResponse DTOs.
     * 
     * @param schedules the list of schedule entities
     * @return the list of schedule responses
     */
    public List<ScheduleResponse> toScheduleResponseList(List<Schedule> schedules) {
        if (schedules == null) {
            return null;
        }
        
        return schedules.stream()
                .map(this::toScheduleResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a new Schedule entity from ScheduleRequest DTO.
     * 
     * @param request the schedule request
     * @param child the child entity
     * @param vaccine the vaccine entity
     * @return the schedule entity
     */
    public Schedule toSchedule(ScheduleRequest request, Child child, Vaccine vaccine) {
        if (request == null) {
            return null;
        }
        
        Schedule schedule = new Schedule();
        schedule.setChild(child);
        schedule.setVaccine(vaccine);
        schedule.setScheduleDate(request.getScheduleDate());
        schedule.setDoseNumber(request.getDoseNumber());
        schedule.setNotes(request.getNotes());
        
        return schedule;
    }
    
    /**
     * Updates Schedule entity from ScheduleRequest DTO.
     * 
     * @param schedule the schedule entity to update
     * @param request the schedule request with updated data
     * @param child the child entity
     * @param vaccine the vaccine entity
     */
    public void updateScheduleFromRequest(Schedule schedule, ScheduleRequest request, 
                                         Child child, Vaccine vaccine) {
        if (schedule == null || request == null) {
            return;
        }
        
        schedule.setChild(child);
        schedule.setVaccine(vaccine);
        schedule.setScheduleDate(request.getScheduleDate());
        schedule.setDoseNumber(request.getDoseNumber());
        schedule.setNotes(request.getNotes());
    }
}
