package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    List<Teacher> findByAcademicRank_AcademicRankId(Integer academicRankId);

    List<Teacher> findByFirstNameIgnoreCase(String firstName);

    List<Teacher> findBySurnameIgnoreCase(String surname);

    List<Teacher> findByFirstNameIgnoreCaseAndSurnameIgnoreCase(String firstName, String surname);
}
