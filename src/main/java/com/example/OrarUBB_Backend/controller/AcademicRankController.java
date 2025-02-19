package com.example.OrarUBB_Backend.controller;

import com.example.OrarUBB_Backend.domain.AcademicRank;
import com.example.OrarUBB_Backend.service.AcademicRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academic-rank")
@RequiredArgsConstructor
@Validated
public class AcademicRankController {
    // endpoint will probably not be used
    private final AcademicRankService academicRankService;

    @GetMapping("/")
    public ResponseEntity<List<AcademicRank>> getAllAcademicRanks() {
        return ResponseEntity.ok(academicRankService.getAllAcademicRanks());
    }
}
