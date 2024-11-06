package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.AcademicRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicRankRepository extends JpaRepository<AcademicRank, Integer> {

    AcademicRank findByAcademicRankId(Integer academicRankId);
}
