package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassInstanceResponse {
    private final UUID classId;
    private final String classDay;
    private final int startHour;
    private final int endHour;
    private final int frequency;
    private final String room;
    private final String formation;
    private final String classType;
    private final String courseInstanceCode;
    private final String teacher;
}
