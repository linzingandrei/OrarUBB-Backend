package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseInstanceResponse {
    private final UUID courseInstanceId;
    private final int courseId;
    private final String courseName;
    private final String courseCode;
}
