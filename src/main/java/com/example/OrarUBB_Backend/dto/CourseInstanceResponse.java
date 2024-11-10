package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseInstanceResponse {
    private UUID courseInstanceId;
    private String courseName;
    private String courseCode;
}
