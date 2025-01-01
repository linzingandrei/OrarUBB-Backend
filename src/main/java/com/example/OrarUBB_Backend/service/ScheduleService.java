package com.example.OrarUBB_Backend.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.domain.Schedule;
import com.example.OrarUBB_Backend.domain.User;
import com.example.OrarUBB_Backend.dto.ScheduleRequest;
import com.example.OrarUBB_Backend.dto.ScheduleResponse;
import com.example.OrarUBB_Backend.repository.ScheduleRepository;
import com.example.OrarUBB_Backend.repository.UserRepository;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public List<ScheduleResponse> getAllSchedulesForCertainUser(String userId) {
        List<Schedule> schedules = scheduleRepository.getAllSchedulesForCertainUser(userId);
        List<ScheduleResponse> scheduleResponse = new ArrayList<>();

        for (var schedule: schedules) {
            ScheduleResponse scheduleResp = new ScheduleResponse(
                schedule.getScheduleId(),
                schedule.getClassId() != null ? schedule.getClassId() : "",
                schedule.getClassDay() != null ? schedule.getClassDay() : "",
                schedule.getStartTime() != null ? schedule.getStartTime() : LocalTime.of(0,0),
                schedule.getEndTime() != null ? schedule.getEndTime() : LocalTime.of(0, 0),
                schedule.getFrequency() != null ? schedule.getFrequency() : -1,
                schedule.getRoomName() != null ? schedule.getRoomName() : "",
                schedule.getFormation() != null ? schedule.getFormation() : "",
                schedule.getClassType() != null ? schedule.getClassType() : "",
                schedule.getCourseInstanceName() != null ? schedule.getCourseInstanceName() : "",
                schedule.getTeacherName() != null ? schedule.getTeacherName() : ""
            );
            
            scheduleResponse.add(scheduleResp);
        }

        return scheduleResponse;
    }

    public Schedule addScheduleForCurrentUser(ScheduleRequest request, String userId) {
        Optional<User> user = this.userRepository.findById(userId); 

        if (user.isPresent()) {
            Schedule schedule = new Schedule();
            int id = 0;
            if (scheduleRepository.getAllSchedulesForCertainUser(userId).size() > 0) {
                id = this.scheduleRepository.getAllSchedulesForCertainUser(userId).getLast().getScheduleId();
            }
            schedule.setScheduleId(id + 1);
            schedule.setUser(user.get());
            schedule.setClassDay(request.getClassDay() != null ? request.getClassDay() : "");
            schedule.setStartTime(request.getStartTime() != null ? request.getStartTime() : LocalTime.of(0, 0));
            schedule.setEndTime(request.getEndTime() != null ? request.getEndTime() : LocalTime.of(0, 0));
            schedule.setFrequency(request.getFrequency() != null ? request.getFrequency() : -1);
            schedule.setRoomName(request.getRoomName() != null ? request.getRoomName() : "");
            schedule.setFormation(request.getFormation() != null ? request.getFormation() : "");
            schedule.setClassType(request.getClassType() != null ? request.getClassType() : "");
            schedule.setCourseInstanceName(request.getCourseInstanceName() != null ? request.getCourseInstanceName() : "");
            schedule.setTeacherName(request.getTeacherName());

            return scheduleRepository.save(schedule);
        }
        else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public Schedule updateAScheduleForCurrentUser(ScheduleRequest request, String userId) {
        Optional<User> user = this.userRepository.findById(userId);

        if (user.isPresent()) {
            int scheduleId = request.getScheduleId();
            System.out.println(scheduleId);

            Optional<Schedule> existingSchedule = this.scheduleRepository.findById(scheduleId);
            System.out.println(existingSchedule.toString());
            if (existingSchedule.isPresent()) {
                Schedule existingSchedulee = existingSchedule.get();
                if (existingSchedulee.getUserId().equals(userId)) {
                    existingSchedulee.setScheduleId(request.getScheduleId());
                    existingSchedulee.setClassDay(request.getClassDay());
                    existingSchedulee.setClassId(request.getClassId());
                    existingSchedulee.setClassType(request.getClassType());
                    existingSchedulee.setCourseInstanceName(request.getCourseInstanceName() != "" ? request.getCourseInstanceName() : "");
                    existingSchedulee.setEndTime(request.getEndTime() != null ? request.getEndTime() : LocalTime.of(0, 0));
                    existingSchedulee.setFormation(request.getFormation());
                    existingSchedulee.setFrequency(request.getFrequency() != null ? request.getFrequency() : -1);
                    existingSchedulee.setRoomName(request.getRoomName());
                    existingSchedulee.setStartTime(request.getStartTime() != null ? request.getStartTime() : LocalTime.of(0, 0));
                    existingSchedulee.setTeacherName(request.getTeacherName());

                    return this.scheduleRepository.save(existingSchedulee);
                }
                else {
                    throw new RuntimeException("No permissions.");
                }
            }
            else {
                throw new RuntimeException("Schedule with id: " + scheduleId + " doesn't exist.");
            }
        }
        else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    public boolean deleteASpecificScheduleForCurrentUser(int scheduleId, String userId) {
        Optional<Schedule> schedule = this.scheduleRepository.findById(scheduleId);

        if (schedule.isPresent()) {
            if (schedule.get().getUserId().equals(userId)) {
                this.scheduleRepository.deleteById(scheduleId);
                return true;
            }
            return false;
        }
        else {
            throw new RuntimeException("Schedule not found with id: " + scheduleId);
        }
    }

    public boolean deleteAllSchedulesForCurrentUser(String userId) {
        int rows = this.scheduleRepository.deleteAllSchedulesForCurrentUser(userId);

        if (rows > 0) {
            return true;
        }
        return false;
    }
}
