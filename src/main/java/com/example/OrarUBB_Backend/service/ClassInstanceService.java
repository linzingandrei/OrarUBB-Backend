package com.example.OrarUBB_Backend.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.OrarUBB_Backend.dto.GroupResponse;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.domain.ClassInstance;
import com.example.OrarUBB_Backend.domain.DayDefinitionLocale;
import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import com.example.OrarUBB_Backend.repository.ClassInstanceRepository;
import com.example.OrarUBB_Backend.repository.DayDefinitionLocaleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassInstanceService {
    private final ClassInstanceRepository classInstanceRepository;
    private final DayDefinitionLocaleRepository dayDefinitionLocaleRepository;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private final TeacherService teacherService;
    private final FormationService formationService;

    public ClassInstanceService(ClassInstanceRepository classInstanceRepository,
                                DayDefinitionLocaleRepository dayDefinitionLocaleRepository, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration, TeacherService teacherService, FormationService formationService) {
        this.classInstanceRepository = classInstanceRepository;
        this.dayDefinitionLocaleRepository = dayDefinitionLocaleRepository;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
        this.teacherService = teacherService;
        this.formationService = formationService;
    }

    public String getClassDayLocalized(int dayId, String language) {
        DayDefinitionLocale dayLocale = dayDefinitionLocaleRepository.findByDayIdAndLanguageTag(dayId, language);
        return dayLocale != null ? dayLocale.getDayNameLocale() : null;
    }

    public List<ClassInstanceResponse> getClassesForGroup(String groupCode, String language) {
        List<ClassInstance> classInstances = new ArrayList<>(List.of());
        if (formationService.isYearCode(groupCode)) {
            List<String> groupsAndSemiGroups = formationService.getGroupsAndSemigroupsForYear(groupCode);
            for (String group : groupsAndSemiGroups) {
                List<ClassInstance> groupClassInstances = classInstanceRepository.findByGroupCode(group);
                classInstances.addAll(groupClassInstances);
            }
        }
        else if (formationService.isGroupCode(groupCode)) {
            List<String> groupsAndSemiGroups = formationService.getComponentsForGroup(groupCode);
            groupsAndSemiGroups.add(formationService.getYearCodeForGroup(groupCode));
            for (String group : groupsAndSemiGroups) {
                List<ClassInstance> groupClassInstances = classInstanceRepository.findByGroupCode(group);
                classInstances.addAll(groupClassInstances);
            }
        }
        else if (formationService.isSubgroupCode(groupCode)) {
            String group = formationService.getGroupForSubgroup(groupCode);
            String year = formationService.getYearCodeForGroup(group);
            List<ClassInstance> subgroupClassInstances = classInstanceRepository.findByGroupCode(groupCode);
            List<ClassInstance> groupClassInstances = classInstanceRepository.findByGroupCode(group);
            List<ClassInstance> yearClassInstances = classInstanceRepository.findByGroupCode(year);
            classInstances.addAll(subgroupClassInstances);
            classInstances.addAll(groupClassInstances);
            classInstances.addAll(yearClassInstances);
        }
        List<ClassInstanceResponse> responseClasses = new ArrayList<>();

        for (ClassInstance classInstance : classInstances) {
            ClassInstanceResponse classInstanceResponse = new ClassInstanceResponse(
                    classInstance.getClassId(),
                    getClassDayLocalized(classInstance.getDayId(), language),
                    classInstance.getStartHour(),
                    classInstance.getEndHour(),
                    classInstance.getFrequency(),
                    classInstanceRepository.findRoomNameByRoomId(classInstance.getRoomId()),
                    classInstanceRepository.findFormationCodeByFormationId(classInstance.getFormationId()),
                    classInstanceRepository.findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(classInstance.getClassTypeId(), language),
                    classInstanceRepository.findCourseInstanceCodeInCourseInstanceByCourseInstanceId(classInstance.getCourseInstanceId()),
                    classInstanceRepository.findCourseNameByCourseInstanceIdAndLanguage(classInstance.getCourseInstanceId(), language),
                    teacherService.getTeacherWithLocalizedNameById(classInstance.getTeacherId(), language).getName()
            );
            responseClasses.add(classInstanceResponse);
        }

        return responseClasses;
    }

    public List<ClassInstanceResponse> getClassesForTeacher(UUID teacherId, String language) {
        List<ClassInstance> classInstances = classInstanceRepository.findByTeacherId(teacherId);

        List<ClassInstanceResponse> responseDTOs = new ArrayList<>();

        for (ClassInstance classInstance : classInstances) {
            ClassInstanceResponse responseDTO = new ClassInstanceResponse(
                    classInstance.getClassId(),
                    getClassDayLocalized(classInstance.getDayId(), language),
                    classInstance.getStartHour(),
                    classInstance.getEndHour(),
                    classInstance.getFrequency(),
                    classInstanceRepository.findRoomNameByRoomId(classInstance.getRoomId()),

                    classInstanceRepository.findFormationCodeByFormationId(classInstance.getFormationId()),
                    classInstanceRepository.findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(classInstance.getClassTypeId(), language),
                    classInstanceRepository.findCourseInstanceCodeInCourseInstanceByCourseInstanceId(classInstance.getCourseInstanceId()),
                    classInstanceRepository.findCourseNameByCourseInstanceIdAndLanguage(classInstance.getCourseInstanceId(), language),
                    classInstanceRepository.findTeacherNameByTeacherId(classInstance.getTeacherId())
            );

            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }


    public List<ClassInstanceResponse> getClassesForCourseInstance(String courseCode, String language) {
        List<ClassInstance> classInstances = classInstanceRepository.findByCourseCode(courseCode);

        List<ClassInstanceResponse> responseDTOs = new ArrayList<>();

        for (ClassInstance classInstance : classInstances) {
            ClassInstanceResponse responseDTO = new ClassInstanceResponse(
                    classInstance.getClassId(),
                    classInstanceRepository.findClassDayByDayIdAndLanguage(classInstance.getDayId(), language),
                    classInstance.getStartHour(),
                    classInstance.getEndHour(),
                    classInstance.getFrequency(),
                    classInstanceRepository.findRoomNameByRoomId(classInstance.getRoomId()),
                    classInstanceRepository.findFormationCodeByFormationId(classInstance.getFormationId()),
                    classInstanceRepository.findClassTypeInClassTypeLocaleByClassTypeIdAndLanguage(classInstance.getClassTypeId(), language),
                    classInstanceRepository.findCourseInstanceCodeInCourseInstanceByCourseInstanceId(classInstance.getCourseInstanceId()),
                    classInstanceRepository.findCourseNameByCourseInstanceIdAndLanguage(classInstance.getCourseInstanceId(), language),
                    teacherService.getTeacherWithLocalizedNameById(classInstance.getTeacherId(), language).getName()
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
