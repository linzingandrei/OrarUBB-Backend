package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.CourseCodeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseCodeNameRepository extends JpaRepository<CourseCodeName, Integer> {

    // Custom query to find a CourseCodeName by course_name
    Optional<CourseCodeName> findByCourseName(String courseName);

    // Custom query to find a CourseCodeName by its abbreviation
    Optional<CourseCodeName> findByCourseNameAbbreviation(String courseNameAbbreviation);
}
