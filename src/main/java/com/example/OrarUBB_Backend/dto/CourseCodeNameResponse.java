package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseCodeNameResponse {
    private UUID courseCodeNameId;
    private String courseName;
    private String courseNameAbbreviation;
}