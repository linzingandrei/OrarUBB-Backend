package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.dto.GroupResponse;
import com.example.OrarUBB_Backend.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final FormationService formationService;

    @Autowired
    public GroupController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping("/id/{academicSpecializationId}/{year}")
    public GroupResponse getAllGroupsWithAcademicSpecializationIdAndYear(@PathVariable int academicSpecializationId, @PathVariable int year) {
        return formationService.getAllGroupsWithAcademicSpecializationIdAndYear(academicSpecializationId, year);
    }

    @GetMapping("/{yearCode}")
    public GroupResponse getAllGroupsWithYearCode(@PathVariable String yearCode) {
        return formationService.getAllGroupsWithYearCode(yearCode);
    }
}
