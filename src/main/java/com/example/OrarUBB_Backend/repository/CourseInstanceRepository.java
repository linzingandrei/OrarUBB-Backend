package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseInstanceRepository extends JpaRepository<CourseInstance, UUID> {

    // Custom query to find a CourseInstance by courseCode
    Optional<CourseInstance> findByCourseCode(String courseCode);

    // Custom query to find a CourseInstance by courseCodeNameId
    Optional<CourseInstance> findByCourseCodeName_CourseCodeNameId(UUID courseCodeNameId);

    // Custom query to find all instances by courseCodeNameId
    List<CourseInstance> findAllByCourseCodeName_CourseCodeNameId(UUID courseCodeNameId);

    @Query("SELECT c FROM CourseCodeNameLocale c WHERE c.languageTag = :languageTag")
    public List<CourseInstance> findByLanguage(@Param("languageTag") String language);

    @Query("SELECT c.courseNameLocale FROM CourseCodeNameLocale c WHERE c.courseCodeNameId = :courseInstanceId AND c.languageTag = :languageTag")
    public String findCourseNameByCourseInstanceIdAndLanguage(@Param("courseInstanceId") UUID courseInstanceId, @Param("languageTag") String language);

    @Query("SELECT c.courseNameAbbreviationLocale FROM CourseCodeNameLocale c WHERE c.courseCodeNameId = :courseInstanceId AND c.languageTag = :languageTag")
    public String findCourseCodeByCourseInstanceIdAndLanguage(@Param("courseInstanceId") UUID courseInstanceId, @Param("languageTag") String language);
}
