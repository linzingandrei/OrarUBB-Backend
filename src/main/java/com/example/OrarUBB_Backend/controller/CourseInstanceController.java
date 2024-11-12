package com.example.OrarUBB_Backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.OrarUBB_Backend.dto.CourseInstanceResponse;
import com.example.OrarUBB_Backend.service.CourseInstanceService;

@RestController
@RequestMapping("/course_instances")
public class CourseInstanceController {
    @Autowired
    private final CourseInstanceService courseInstanceService;

    public CourseInstanceController(CourseInstanceService courseInstanceService) {
        this.courseInstanceService = courseInstanceService;
    }

    @GetMapping("/{language}")
    public ResponseEntity<List<CourseInstanceResponse>> getCourseFewDetailsInSpecifiedLanguage(
        @PathVariable("language") String language) {
        List<CourseInstanceResponse> classes = courseInstanceService.getCoursesSmallDetailsInSpecifiedLanguage(language);

        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{course_code}/{language}")
    public ResponseEntity<List<CourseInstanceResponse>> getCourseDetailsForCourseCodeInSpecifiedLanguage(
        @PathVariable("course_code") UUID courseCode,
        @PathVariable("language") String language) {
        List<CourseInstanceResponse> classes = courseInstanceService.getCourseDetailsForCourseCodeInSpecifiedLanguage(courseCode, language);
        return ResponseEntity.ok(classes);
    }
}
