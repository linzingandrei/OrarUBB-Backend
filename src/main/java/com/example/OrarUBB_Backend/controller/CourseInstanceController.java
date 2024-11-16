package com.example.OrarUBB_Backend.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
    private final CourseInstanceService courseInstanceService;

    public CourseInstanceController(CourseInstanceService courseInstanceService) {
        this.courseInstanceService = courseInstanceService;
    }

    @GetMapping("/{language}")
    public ResponseEntity<List<CourseInstanceResponse>> getCourseFewDetailsInSpecifiedLanguage(
            // BROKEN
        @PathVariable("language") String language) {
        
        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");
        
        if (validLanguages.contains(language)) {
            List<CourseInstanceResponse> classes = courseInstanceService.getCoursesSmallDetailsInSpecifiedLanguage(language);
            return ResponseEntity.ok(classes);
        }

        return ResponseEntity.badRequest().build();
    }
}
