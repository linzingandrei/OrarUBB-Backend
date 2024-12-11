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
        List<ClassInstanceResponse> results = new ArrayList<>();
        if (formationService.isYearCode(groupCode)) {
            List<Object[]> queryResults = classInstanceRepository.findClassInstancesByGroupAndLanguageTag(groupCode, language);
            results.addAll(this.mapObjectsToClassInstanceResponse(queryResults));
        }
        else if (formationService.isGroupCode(groupCode)) {
            List<String> groupsAndSemiGroups = formationService.getComponentsForGroup(groupCode);
            groupsAndSemiGroups.add(formationService.getYearCodeForGroup(groupCode));
            List<Object[]> queryResults = new ArrayList<>();
            for (String group : groupsAndSemiGroups) {
                queryResults = new ArrayList<>();
                queryResults.addAll(classInstanceRepository.findClassInstancesByGroupAndLanguageTag(groupCode, language));
            }
            results.addAll(this.mapObjectsToClassInstanceResponse(queryResults));
        }
        else if (formationService.isSubgroupCode(groupCode)) {
            String group = formationService.getGroupForSubgroup(groupCode);
            String year = formationService.getYearCodeForGroup(group);
            List<Object[]> queryResults = classInstanceRepository.findClassInstancesByGroupAndLanguageTag(groupCode, language);
            queryResults.addAll(classInstanceRepository.findClassInstancesByGroupAndLanguageTag(group, language));
            queryResults.addAll(classInstanceRepository.findClassInstancesByGroupAndLanguageTag(year, language));
            results.addAll(this.mapObjectsToClassInstanceResponse(queryResults));
        }
        return results;
    }

    public List<ClassInstanceResponse> getClassesForTeacher(String teacherCodeName, String language) {

        List<Object[]> results = classInstanceRepository.findClassInstanceByTeacherAndLanguageTag(teacherCodeName, language);


        return mapObjectsToClassInstanceResponse(results);
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

    public List<ClassInstanceResponse> getClassesForRoom(String roomName, String language) {
        List<Object[]> queryResults = classInstanceRepository.findClassInstancesByRoomNameAndLanguageTag(roomName, language);
        return this.mapObjectsToClassInstanceResponse(queryResults);
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



    public List<ClassInstanceResponse> mapObjectsToClassInstanceResponse(List<Object[]> results) {
        List<ClassInstanceResponse> responseDTOs = new ArrayList<>();
        for (Object[] result : results) {
            UUID classId = (UUID) result[0];
            String classDay = (String) result[1];
            int startHour = (Integer) result[2];
            int endHour = (Integer) result[3];
            int frequency = (Integer) result[4];
            String room = (String) result[5];
            String formation = (String) result[6];
            String classType = (String) result[7];
            String courseInstanceCode = (String) result[8];
            String courseInstanceName = (String) result[9];
            String academicRankAbbreviation = (String) result[10];
            String firstName = (String) result[11];
            String surname = (String) result[12];

            // Concatenate academic rank abbreviation, first name, and surname to form teacher
            String teacher = academicRankAbbreviation + " " + firstName + " " + surname;

            ClassInstanceResponse response = new ClassInstanceResponse(
                    classId, classDay, startHour, endHour, frequency, room, formation, classType,
                    courseInstanceCode, courseInstanceName, teacher
            );
            responseDTOs.add(response);

        }
        return responseDTOs;
    }
}
