package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.CourseCodeName;
import com.example.OrarUBB_Backend.repository.CourseCodeNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseCodeNameService {

    @Autowired
    private CourseCodeNameRepository courseCodeNameRepository;

    public Optional<CourseCodeName> getCourseCodeNameByName(String courseName) {
        return courseCodeNameRepository.findByCourseName(courseName);
    }

    public Optional<CourseCodeName> getCourseCodeNameByAbbreviation(String abbreviation) {
        return courseCodeNameRepository.findByCourseNameAbbreviation(abbreviation);
    }

    public CourseCodeName saveCourseCodeName(CourseCodeName courseCodeName) {
        return courseCodeNameRepository.save(courseCodeName);
    }
}
