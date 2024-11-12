package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.CourseInstance;
import com.example.OrarUBB_Backend.dto.CourseInstanceResponse;
import com.example.OrarUBB_Backend.repository.CourseInstanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseInstanceService {
    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    // Method to get a CourseInstance by courseCode
    public Optional<CourseInstance> getCourseInstanceByCourseCode(String courseCode) {
        return courseInstanceRepository.findByCourseCode(courseCode);
    }

    // Method to get a CourseInstance by courseCodeNameId
    public Optional<CourseInstance> getCourseInstanceByCourseCodeNameId(UUID courseCodeNameId) {
        return courseInstanceRepository.findByCourseCodeName_CourseCodeNameId(courseCodeNameId);
    }

    // Method to get all instances for a given courseCodeNameId
    public List<CourseInstance> getAllInstancesByCourseCodeNameId(UUID courseCodeNameId) {
        return courseInstanceRepository.findAllByCourseCodeName_CourseCodeNameId(courseCodeNameId);
    }

    public List<CourseInstanceResponse> getCoursesSmallDetailsInSpecifiedLanguage(String language) {
        List<CourseInstance> courseInstances = courseInstanceRepository.findByLanguage(language);

        List<CourseInstanceResponse> responseDTOs = new ArrayList<>();

        for (CourseInstance courseInstance: courseInstances) {
            CourseInstanceResponse responseDTO = new CourseInstanceResponse(
                courseInstance.getCourseInstanceId(),
                courseInstance.getCourseId(),
                courseInstanceRepository.findCourseCodeByCourseInstanceIdAndLanguage(courseInstance.getCourseInstanceId(), language),
                courseInstanceRepository.findCourseNameByCourseInstanceIdAndLanguage(courseInstance.getCourseInstanceId(), language)
            );

            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }
}
 