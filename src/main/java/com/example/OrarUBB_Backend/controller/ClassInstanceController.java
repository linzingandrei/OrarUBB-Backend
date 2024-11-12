package com.example.OrarUBB_Backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import com.example.OrarUBB_Backend.service.ClassInstanceService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/classes")
public class ClassInstanceController {
    @Autowired
    private final ClassInstanceService classInstanceService;

    public ClassInstanceController(ClassInstanceService classInstanceService) {
        this.classInstanceService = classInstanceService;
    }

    @GetMapping("/teacher/{teacher_id}/{language}")
    public ResponseEntity<List<ClassInstanceResponse>> getClassesForTeacher(
        @PathVariable("teacher_id") UUID teacherId,
        @PathVariable("language") String language) {

        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");

        if (validLanguages.contains(language)) {
            List<ClassInstanceResponse> classes = classInstanceService.getClassesForTeacher(teacherId, language);
            return ResponseEntity.ok(classes);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{course_code}/{language}")
    public ResponseEntity<List<ClassInstanceResponse>> getCourseDetailsForCourseCodeInSpecifiedLanguage(
        @PathVariable("course_code") String courseCode,
        @PathVariable("language") String language) {
        
        // TODO: Verify course_code is always valid
        
        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");
        
        if (validLanguages.contains(language)) { 
            List<ClassInstanceResponse> classes = classInstanceService.getClassesForCourseInstance(courseCode, language);
            return ResponseEntity.ok(classes);
        }

        return ResponseEntity.badRequest().build();
    }
}