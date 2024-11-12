package com.example.OrarUBB_Backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.domain.ClassInstance;
import com.example.OrarUBB_Backend.domain.DayDefinitionLocale;
import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import com.example.OrarUBB_Backend.repository.ClassInstanceRepository;
import com.example.OrarUBB_Backend.repository.DayDefinitionLocaleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassInstanceService {
    @Autowired
    private final ClassInstanceRepository classInstanceRepository;
    @Autowired
    private DayDefinitionLocaleRepository dayDefinitionLocaleRepository;

    public String getClassDayLocalized(UUID dayId, String language) {
        DayDefinitionLocale dayLocale = dayDefinitionLocaleRepository.findByDayIdAndLanguageTag(dayId, language);
        return dayLocale != null ? dayLocale.getDayNameLocale() : null;
    }

    public List<ClassInstanceResponse> getClassesForTeacher(UUID teacherId, String language) {
        List<ClassInstance> classInstances = classInstanceRepository.findByTeacherId(teacherId);

        List<ClassInstanceResponse> responseDTOs = new ArrayList<>();

        for (ClassInstance classInstance: classInstances) {
            String localizedClassDay = getClassDayLocalized(classInstance.getClassId(), language);

            ClassInstanceResponse responseDTO = new ClassInstanceResponse(
                classInstance.getClassId(),
                localizedClassDay,
                classInstance.getStartHour(),
                classInstance.getEndHour(),
                classInstance.getFrequency(),
                classInstanceRepository.findRoomNameByRoomId(classInstance.getRoomId()),
                classInstanceRepository.findFormationCodeByRoomId(classInstance.getFormationId()),
                classInstanceRepository.findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(classInstance.getClassTypeId(), language),
                classInstanceRepository.findCourseInstanceCodeInCourseInstanceByCourseInstanceId(classInstance.getCourseInstanceId()),
                classInstanceRepository.findTeacherNameByTeacherId(classInstance.getTeacherId())
            );

            responseDTOs.add(responseDTO);
        }
        
        return responseDTOs;
    }

    public List<ClassInstanceResponse> getClassesForCourseInstance(String courseCode, String language) {
        List<ClassInstance> classInstances = classInstanceRepository.findByCourseCode(courseCode);

        List<ClassInstanceResponse> responseDTOs = new ArrayList<>();

        for (ClassInstance classInstance: classInstances) {
            ClassInstanceResponse responseDTO = new ClassInstanceResponse(
                classInstance.getClassId(),
                classInstanceRepository.findClassDayByDayIdAndLanguage(classInstance.getDayId(), language),
                classInstance.getStartHour(),
                classInstance.getEndHour(),
                classInstance.getFrequency(),
                classInstanceRepository.findRoomNameByRoomId(classInstance.getRoomId()),
                classInstanceRepository.findFormationCodeByRoomId(classInstance.getFormationId()),
                classInstanceRepository.findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(classInstance.getClassTypeId(), language),
                classInstanceRepository.findCourseInstanceCodeInCourseInstanceByCourseInstanceId(classInstance.getCourseInstanceId()),
                classInstanceRepository.findTeacherNameByTeacherId(classInstance.getTeacherId())
            );

            responseDTOs.add(responseDTO);
        }

        return responseDTOs; 
    }

    public List<ClassInstance> getAllClassInstances() {
        return classInstanceRepository.findAll();
    }

    public ClassInstance getClassInstanceById(UUID classInstanceId) {
        Optional<ClassInstance> optionalClassInstace = classInstanceRepository.findById(classInstanceId);
        
        if (optionalClassInstace.isPresent()) {
            return optionalClassInstace.get();
        }

        log.info("Class instance with id: {} doesn't exist", classInstanceId);
        return null;
    }

    public ClassInstance saveClassInstance(ClassInstance classInstance) {
        ClassInstance savedClassInstance = classInstanceRepository.save(classInstance);

        log.info("Class instance with id: {} saved succesfully", classInstance.getClassId());
        return savedClassInstance;
    }

    public ClassInstance updateClassInstance(ClassInstance classInstance) {
        ClassInstance updatedClassInstance = classInstanceRepository.save(classInstance);

        log.info("Class instance with id: {} updated succesfully", classInstance.getClassId());

        return updatedClassInstance;
    }

    public void deleteClassInstance(UUID classInstanceId) {
        classInstanceRepository.deleteById(classInstanceId);

        log.info("Class instance with id: {} deleted succesfully", classInstanceId);
    }
}
