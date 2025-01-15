package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.service.FormationService;
import com.example.OrarUBB_Backend.service.UserClassRelationService;
import org.springframework.web.bind.annotation.RestController;

import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import com.example.OrarUBB_Backend.service.ClassInstanceService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/classes")
public class ClassInstanceController {
    private final ClassInstanceService classInstanceService;
    private final UserClassRelationService userClassRelationService;

    private final FormationService formationService;

    public ClassInstanceController(ClassInstanceService classInstanceService, FormationService formationService, UserClassRelationService userClassRelationService) {
        this.classInstanceService = classInstanceService;
        this.formationService = formationService;
        this.userClassRelationService = userClassRelationService;
    }

    @GetMapping("/teacher/{teacher_code_name}/{language}")
    public ResponseEntity<List<ClassInstanceResponse>> getClassesForTeacher(
            @PathVariable("teacher_code_name") String teacherCodeName,
            @PathVariable("language") String language) {

        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");

        if (validLanguages.contains(language)) {
            List<ClassInstanceResponse> classes = classInstanceService.getClassesForTeacher(teacherCodeName, language);
            return ResponseEntity.ok(classes);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/group/{group_code}/{language}")
    public ResponseEntity<List<ClassInstanceResponse>> getClassesForGroup(
            // Should work for the year: /group/IE2/ro-RO and return for IE2, 921..927, 921/1, 921/2, 922/1, 922/2 etc
            // For the group: /group/925/ro-RO and return for IE2, 925/1, 925/2, 925
            // For the subgroup: /group/925-1/ro-RO and return for IE2, 925/1, 925
            // Thanks to Horatiu, now it's extremely efficient :)
            @PathVariable("group_code") String groupCode,
            @PathVariable("language") String language) {

        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");
        groupCode = groupCode.replace("-", "/");
        if (validLanguages.contains(language)) {
            List<ClassInstanceResponse> classes = classInstanceService.getClassesForGroup(groupCode, language);
            return ResponseEntity.ok(classes);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/room/{roomName}/{language}")
    public ResponseEntity<List<ClassInstanceResponse>> getClassesForRoom(
            @PathVariable("roomName") String roomName,
            @PathVariable("language") String language) {

        Set<String> validLanguages = Set.of("ro-RO", "en-GB", "de-DE", "hu-HU");

        if (roomName.contains("-")) {
            switch (roomName)
            {
                case "2-I", "5-I", "6-II", "7-I", "9-I" -> roomName = roomName.replace("-", "/");
                default -> {}
            }
        }

        if (validLanguages.contains(language)) {
            List<ClassInstanceResponse> classes = classInstanceService.getClassesForRoom(roomName, language);
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
