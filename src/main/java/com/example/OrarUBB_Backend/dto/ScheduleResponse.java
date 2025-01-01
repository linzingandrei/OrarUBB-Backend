package com.example.OrarUBB_Backend.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private int scheduleId;
    private String classId;
    private String classDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private int frequency;
    private String roomName;
    private String formation;
    private String classType;
    private String courseInstanceName;
    private String teacherName;
}
