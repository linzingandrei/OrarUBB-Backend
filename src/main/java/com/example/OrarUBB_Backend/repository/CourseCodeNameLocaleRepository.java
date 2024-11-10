package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.CourseCodeNameLocale;
import com.example.OrarUBB_Backend.domain.CourseCodeNameLocale.CourseCodeNameLocaleId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseCodeNameLocaleRepository extends JpaRepository<CourseCodeNameLocale, CourseCodeNameLocaleId> {

    // Custom query to find a localized course name by courseCodeNameId and languageTag
    Optional<CourseCodeNameLocale> findByCourseCodeNameIdAndLanguageTag(UUID courseCodeNameId, String languageTag);

    // Custom query to find all locales for a specific courseCodeNameId
    List<CourseCodeNameLocale> findAllByCourseCodeNameId(UUID courseCodeNameId);
}
