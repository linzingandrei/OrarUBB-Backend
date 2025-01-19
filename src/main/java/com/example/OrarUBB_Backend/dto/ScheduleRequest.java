package com.example.OrarUBB_Backend.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleRequest {
    private int scheduleId;
    private String userId;
    private String classId;
    private String classDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer frequency;
    private String roomName;
    private String formation;
    private String classType;
    private String courseInstanceName;
    private String teacherName;
}
