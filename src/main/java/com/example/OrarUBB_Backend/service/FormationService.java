package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.Formation;
import com.example.OrarUBB_Backend.dto.GroupResponse;
import com.example.OrarUBB_Backend.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FormationService {
    private final FormationRepository formationRepository;
    private final AcademicSpecializationService academicSpecializationService;

    @Autowired
    public FormationService(FormationRepository formationRepository, AcademicSpecializationService academicSpecializationService) {
        this.formationRepository = formationRepository;
        this.academicSpecializationService = academicSpecializationService;
    }

    public List<Formation> getAllFormationsForAcademicSpecialization(int academicSpecializationId) {
        return formationRepository.findByAcademicSpecialization_AcademicSpecializationId(academicSpecializationId);
    }

    public GroupResponse getAllGroupsWithAcademicSpecializationIdAndYear(int academicSpecializationId, int year) {
        List<String> formationCodes = formationRepository.getAllGroupsForSpecializationInAYear(academicSpecializationId, year);
        List<String> result = new ArrayList<>(formationCodes);
        for(String code : formationCodes) {
            String components = formationRepository.getComponentsForGroup(code);
            String[] subgroups = components.split(";");
            if (subgroups.length >= 2) {
                result.add(subgroups[0]);
                result.add(subgroups[1]);
            }
        }
        return new GroupResponse(result);
    }

    public GroupResponse getAllGroupsWithYearCode(String year_code) {
        return new GroupResponse(formationRepository.getAllGroupsWithYearCode(year_code));
    }

    public Boolean isYearCode(String code) {
        return this.formationRepository.getYearCode(code).size() == 1;
    }

    public Boolean isGroupCode(String code) {
        return this.formationRepository.getGroupCode(code).size() == 1;
    }

    public Boolean isSubgroupCode(String code) {
        return this.formationRepository.getSubgroupCode(code).size() == 1;
    }

    public String getYearCodeForGroup(String code) {
        if (isGroupCode(code)) {
            return this.formationRepository.getYearCodeForGroup(code);
        }
        return null;
    }

    public List<String> getComponentsForGroup(String code) {
        if (isGroupCode(code)) {
            List<String> components = new ArrayList<>(Arrays.asList(this.formationRepository.getComponentsForGroup(code).split(";")));
            components.add(code);
            return components;
        }
        return null;
    }

    public String getGroupForSubgroup(String code) {
        if (isSubgroupCode(code)) {
            return this.formationRepository.getGroupForSemiGroup(code);
        }
        return null;
    }

    public List<String> getGroupsAndSemigroupsForYear(String yearCode) {
        if (isYearCode(yearCode)) {
            List<String> groupsAndSemiGroups = new ArrayList<>();
            groupsAndSemiGroups.add(yearCode);

            String[] groupsForYear = this.formationRepository.getGroupsForYearCode(yearCode).split(";");

            for (String group : groupsForYear) {
                groupsAndSemiGroups.addAll(this.getComponentsForGroup(group));
            }

            return groupsAndSemiGroups;
//            List<String> groupsAndSemiGroups = new ArrayList<>();
//
//            String[] groupsForYear =this.formationRepository.getGroupsForYearCode(yearCode).split(";");

        }
        return null;
    }
}
