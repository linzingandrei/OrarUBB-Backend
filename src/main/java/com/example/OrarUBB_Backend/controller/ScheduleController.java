package com.example.OrarUBB_Backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.OrarUBB_Backend.domain.Schedule;
import com.example.OrarUBB_Backend.dto.ScheduleRequest;
import com.example.OrarUBB_Backend.dto.ScheduleResponse;
import com.example.OrarUBB_Backend.service.ScheduleService;

import io.micrometer.core.ipc.http.HttpSender.Response;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/my-account")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{userId}/personal_schedule")
    public List<ScheduleResponse> getAllSchedulesForCertainUser(@PathVariable("userId") String userId) {
        return scheduleService.getAllSchedulesForCertainUser(userId);
    }

    @PostMapping("/{userId}/personal_schedule")
    public ResponseEntity<Schedule> addScheduleForCertainUser(@PathVariable("userId") String userId, @RequestBody ScheduleRequest request) {
        Schedule savedSchedule = scheduleService.addScheduleForCurrentUser(request, userId);

        return ResponseEntity.ok(savedSchedule);    
    }

    @PutMapping("/{userId}/personal_schedule")
    public ResponseEntity<ScheduleResponse> updateScheduleForCertainUser(@PathVariable("userId") String userId, @RequestBody ScheduleRequest request) {
        Schedule updateScheduleFromService = this.scheduleService.updateAScheduleForCurrentUser(request, userId);
        ScheduleResponse updatedScheduleResponse = new ScheduleResponse();

        updatedScheduleResponse.setScheduleId(updateScheduleFromService.getScheduleId());
        updatedScheduleResponse.setClassId(updateScheduleFromService.getClassId());
        updatedScheduleResponse.setClassDay(updateScheduleFromService.getClassDay());
        updatedScheduleResponse.setStartTime(updateScheduleFromService.getStartTime());
        updatedScheduleResponse.setEndTime(updateScheduleFromService.getEndTime());
        updatedScheduleResponse.setFrequency(updateScheduleFromService.getFrequency());
        updatedScheduleResponse.setRoomName(updateScheduleFromService.getRoomName());
        updatedScheduleResponse.setFormation(updateScheduleFromService.getFormation());
        updatedScheduleResponse.setClassType(updateScheduleFromService.getClassType());
        updatedScheduleResponse.setCourseInstanceName(updateScheduleFromService.getCourseInstanceName());
        updatedScheduleResponse.setTeacherName(updateScheduleFromService.getTeacherName());

        System.out.println(updatedScheduleResponse.toString());
        
        return ResponseEntity.ok(updatedScheduleResponse);
    }

    @DeleteMapping("/{userId}/personal_schedule/{scheduleId}")
    public ResponseEntity<List<ScheduleResponse>> deleteScheduleByIdForCurrentUser(@PathVariable("userId") String userId, @PathVariable("scheduleId") int scheduleId) {
        boolean isDeleted = scheduleService.deleteASpecificScheduleForCurrentUser(scheduleId, userId);

        if (isDeleted) {
            // System.out.println("A AJUNS");
            List<ScheduleResponse> updatedScheduleList = scheduleService.getAllSchedulesForCertainUser(userId);
            for (ScheduleResponse scheduleResponse: updatedScheduleList) {
                System.out.println(scheduleResponse.toString());
            }
            return ResponseEntity.ok(updatedScheduleList);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{userId}/personal_schedule")
    public ResponseEntity<Map<String, String>> deleteAllSchedulesForCurrentUser(@PathVariable("userId") String userId) {
        boolean isDeleted = scheduleService.deleteAllSchedulesForCurrentUser(userId);

        if (isDeleted) {
            Map<String, String> response = Map.of("message", "All schedules deleted.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = Map.of("error", "Something went wrong when deleting all schedules.");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
}
