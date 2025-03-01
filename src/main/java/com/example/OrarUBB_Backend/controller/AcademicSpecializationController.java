package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.domain.AcademicSpecializationLocale;
import com.example.OrarUBB_Backend.dto.AcademicSpecializationLocaleResponse;
import com.example.OrarUBB_Backend.service.AcademicSpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academic-specializations")
public class AcademicSpecializationController {
    private final AcademicSpecializationService academicSpecializationService;

//    @Autowired
    public AcademicSpecializationController(AcademicSpecializationService academicSpecializationService) {
        this.academicSpecializationService = academicSpecializationService;
    }

    @GetMapping("/{level}/{language}")
    public List<AcademicSpecializationLocaleResponse> getAllAcademicSpecializationsForLevelAndLanguage(@PathVariable String level, @PathVariable String language) {
        return academicSpecializationService.getAllAcademicSpecializationsForLevelAndLanguage(level, language);
    }

    @GetMapping("/{abbreviation}")
    public Integer getIdForAcademicSpecializationAbbreviation(@PathVariable String abbreviation) {
        List<AcademicSpecializationLocale> academicSpecializationLocale = academicSpecializationService.getAcademicSpecializationWithAbbrev(abbreviation);
        return academicSpecializationLocale.getFirst().getAcademicSpecializationId();
    }
}
