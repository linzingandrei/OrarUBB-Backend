package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.CourseCodeNameLocale;
import com.example.OrarUBB_Backend.repository.CourseCodeNameLocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseCodeNameLocaleService {

    @Autowired
    private CourseCodeNameLocaleRepository courseCodeNameLocaleRepository;

    public Optional<CourseCodeNameLocale> getLocalizedCourseName(int courseCodeNameId, String languageTag) {
        return courseCodeNameLocaleRepository.findByCourseCodeNameIdAndLanguageTag(courseCodeNameId, languageTag);
    }

    public List<CourseCodeNameLocale> getAllLocalesForCourse(int courseCodeNameId) {
        return courseCodeNameLocaleRepository.findAllByCourseCodeNameId(courseCodeNameId);
    }
}
