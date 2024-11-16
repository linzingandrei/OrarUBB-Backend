package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.dto.TeacherResponse;
import com.example.OrarUBB_Backend.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{language}")
    public List<TeacherResponse> getTeachersByLanguage(@PathVariable("language") String language) {
        return teacherService.getTeachersWithLocalizedNames(language);
    }
}
