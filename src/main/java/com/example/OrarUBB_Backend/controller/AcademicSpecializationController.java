package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.domain.AcademicRankLocale;
import com.example.OrarUBB_Backend.dto.AcademicSpecializationLocaleResponse;
import com.example.OrarUBB_Backend.service.AcademicSpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academicSpecializations")
public class AcademicSpecializationController {
    private final AcademicSpecializationService academicSpecializationService;

    @Autowired
    public AcademicSpecializationController(AcademicSpecializationService academicSpecializationService) {
        this.academicSpecializationService = academicSpecializationService;
    }

    @GetMapping("/:level/:language")
    public List<AcademicSpecializationLocaleResponse> getAllAcademicSpecializationsForLevelAndLanguage(@PathVariable String level, @PathVariable String language) {
        return academicSpecializationService.getAllAcademicSpecializationsForLevelAndLanguage(level, language);
    }
}
