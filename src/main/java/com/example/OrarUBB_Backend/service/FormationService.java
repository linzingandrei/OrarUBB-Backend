package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.Formation;
import com.example.OrarUBB_Backend.dto.GroupResponse;
import com.example.OrarUBB_Backend.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new GroupResponse(formationRepository.getAllGroupsForSpecializationInAYear(academicSpecializationId, year));
    }

    public GroupResponse getAllGroupsWithYearCode(String year_code)
    {

        return new GroupResponse(formationRepository.getAllGroupsWithYearCode(year_code));
    }
}
