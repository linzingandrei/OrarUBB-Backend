package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.dto.TeacherResponse;
import com.example.OrarUBB_Backend.service.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{language}")
    public List<TeacherResponse> getTeachersByLanguage(@PathVariable String language) {
        return teacherService.getTeachersWithLocalizedNames(language);
    }
}
