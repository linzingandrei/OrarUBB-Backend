package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.AcademicRank;
import com.example.OrarUBB_Backend.repository.AcademicRankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
@Slf4j
public class AcademicRankService {
    private final AcademicRankRepository academicRankRepository;

    public AcademicRankService(AcademicRankRepository academicRankRepository) {
        this.academicRankRepository = academicRankRepository;
        this.academicRankRepository.save(new AcademicRank(1, "Profesor"));
        this.academicRankRepository.save(new AcademicRank(2, "Conferentiar Universitar"));
    }

    public List<AcademicRank> getAllAcademicRanks() {
        return academicRankRepository.findAll();
    }


}
