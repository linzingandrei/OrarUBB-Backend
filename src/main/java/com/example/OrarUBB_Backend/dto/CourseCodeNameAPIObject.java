package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class CourseCodeNameAPIObject {

    private final UUID courseCodeNameId;
    private final String courseName;
    private final String courseNameAbbreviation;

    public CourseCodeNameAPIObject(UUID courseCodeNameId, String courseName, String courseNameAbbreviation) {
        this.courseCodeNameId = courseCodeNameId;
        this.courseName = courseName;
        this.courseNameAbbreviation = courseNameAbbreviation;
    }

    public UUID getCourseCodeNameId() {
        return courseCodeNameId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseNameAbbreviation() {
        return courseNameAbbreviation;
    }
}