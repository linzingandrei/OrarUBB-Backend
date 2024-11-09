package com.example.OrarUBB_Backend.service;
import com.example.OrarUBB_Backend.domain.AcademicSpecialization;
import com.example.OrarUBB_Backend.repository.AcademicSpecializationLocaleRepository;
import com.example.OrarUBB_Backend.repository.AcademicSpecializationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AcademicSpecializationService {

    private final AcademicSpecializationRepository academicSpecializationRepository;
    private final AcademicSpecializationLocaleRepository academicSpecializationLocaleRepository;

    public AcademicSpecializationService(
            AcademicSpecializationRepository academicSpecializationRepository,
            AcademicSpecializationLocaleRepository academicSpecializationLocaleRepository) {
        this.academicSpecializationRepository = academicSpecializationRepository;
        this.academicSpecializationLocaleRepository = academicSpecializationLocaleRepository;
    }

    public List<AcademicSpecialization> getAllAcademicSpecializations() {
        return academicSpecializationRepository.findAll();
    }

    // #TODO: implement all CRUD operations
}
