package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.CourseInstance;
import com.example.OrarUBB_Backend.dto.CourseInstanceResponse;
import com.example.OrarUBB_Backend.repository.CourseInstanceRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseInstanceService {
    private final CourseInstanceRepository courseInstanceRepository;

    public CourseInstanceService(CourseInstanceRepository courseInstanceRepository) {
        this.courseInstanceRepository = courseInstanceRepository;
    }

    // Method to get a CourseInstance by courseCode
    public Optional<CourseInstance> getCourseInstanceByCourseCode(String courseCode) {
        return courseInstanceRepository.findByCourseCode(courseCode);
    }

    // Method to get a CourseInstance by courseCodeNameId
    public Optional<CourseInstance> getCourseInstanceByCourseCodeNameId(int courseCodeNameId) {
        return courseInstanceRepository.findByCourseCodeName_CourseCodeNameId(courseCodeNameId);
    }

    // Method to get all instances for a given courseCodeNameId
    public List<CourseInstance> getAllInstancesByCourseCodeNameId(int courseCodeNameId) {
        return courseInstanceRepository.findAllByCourseCodeName_CourseCodeNameId(courseCodeNameId);
    }

    public List<CourseInstanceResponse> getCoursesSmallDetailsInSpecifiedLanguage(String language) {
        long startTime = System.nanoTime();
        List<CourseInstance> courseInstances = courseInstanceRepository.findByLanguage(language);

        List<CourseInstanceResponse> responseDTOs = new ArrayList<>();

        for (CourseInstance courseInstance: courseInstances) {
            CourseInstanceResponse responseDTO = new CourseInstanceResponse(
                courseInstance.getCourseInstanceId(),
                courseInstance.getCourseId(),
                courseInstanceRepository.findCourseNameByCourseInstanceIdAndLanguage(courseInstance.getCourseId(), language),
                courseInstanceRepository.findCourseCodeByCourseInstanceIdAndLanguage(courseInstance.getCourseId(), language)
            );

            responseDTOs.add(responseDTO);
        }
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) + " ns");

        return responseDTOs;
    }

    public List<CourseInstanceResponse> getAllCourseInstancesWithCodeAndLocalizedName(String language)
    {
        long startTime = System.nanoTime();
        List <CourseInstanceResponse> courseInstances = courseInstanceRepository.getAllCourseInstancesWithCodeAndLocalizedName(language);
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) + " ns");
        return courseInstances;
    }
}
 