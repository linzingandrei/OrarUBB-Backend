package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.Formation;
import com.example.OrarUBB_Backend.dto.GroupResponse;
import com.example.OrarUBB_Backend.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<GroupResponse> getAllGroupsForAcademicSpecializationAndYear(int academicSpecializationId, int year) {
        return getAllFormationsForAcademicSpecialization(academicSpecializationId).stream()
                .filter(formation -> formation.getYear() == year)
                .map(formation -> new GroupResponse(formation.getComponents()))
                .toList();
    }
}
