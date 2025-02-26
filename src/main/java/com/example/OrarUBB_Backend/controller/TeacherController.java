package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.dto.TeacherResponse;
import com.example.OrarUBB_Backend.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{language}")
    public List<TeacherResponse> getTeachersByLanguage(@PathVariable("language") String language) {
	System.out.println(teacherService.getTeachersWithLocalizedNames(language));
        return teacherService.getTeachersWithLocalizedNames(language);
    }

    @GetMapping("/id/{teacher_id}/{language}")
    public TeacherResponse getTeacherById(@PathVariable("teacher_id") UUID teacherId, @PathVariable("language") String language) {
        return teacherService.getTeacherWithLocalizedNameById(teacherId, language);
    }

    @GetMapping("/code/{teacher_code_name}/{language}")
    public TeacherResponse getTeacherByCodeName(@PathVariable("teacher_code_name") String codeName, @PathVariable("language") String language) {
        return teacherService.getTeacherWithLocalizedNameByCodeName(codeName, language);
    }

}
