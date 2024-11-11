package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class CourseInstanceAPIObject {

    private final UUID courseInstanceId;
    private final String courseName;
    private final String courseCode;

    public CourseInstanceAPIObject(UUID courseInstanceId, String courseName, String courseCode) {
        this.courseInstanceId = courseInstanceId;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public UUID getCourseInstanceId() {
        return courseInstanceId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
