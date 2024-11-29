package com.example.OrarUBB_Backend.service;
import com.example.OrarUBB_Backend.domain.AcademicSpecialization;
import com.example.OrarUBB_Backend.domain.AcademicSpecializationLocale;
import com.example.OrarUBB_Backend.dto.AcademicSpecializationLocaleResponse;
import com.example.OrarUBB_Backend.dto.AcademicSpecializationResponse;
import com.example.OrarUBB_Backend.repository.AcademicSpecializationLocaleRepository;
import com.example.OrarUBB_Backend.repository.AcademicSpecializationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AcademicSpecializationService {

    private final AcademicSpecializationRepository academicSpecializationRepository;
    private final AcademicSpecializationLocaleRepository academicSpecializationLocaleRepository;

    @Autowired
    public AcademicSpecializationService(
            AcademicSpecializationRepository academicSpecializationRepository,
            AcademicSpecializationLocaleRepository academicSpecializationLocaleRepository) {
        this.academicSpecializationRepository = academicSpecializationRepository;
        this.academicSpecializationLocaleRepository = academicSpecializationLocaleRepository;
    }

    public AcademicSpecializationResponse convertToResponse(AcademicSpecialization academicSpecialization) {
        return new AcademicSpecializationResponse(
                academicSpecialization.getAcademicSpecializationId(),
                academicSpecialization.getInternalName(),
                academicSpecialization.getLocales().stream()
                        .map(this::convertLocaleToResponse)
                        .collect(Collectors.toSet())
        );
    }

    public AcademicSpecializationLocaleResponse convertLocaleToResponse(AcademicSpecializationLocale academicSpecializationLocale) {
        return new AcademicSpecializationLocaleResponse(
                academicSpecializationLocale.getAcademicSpecializationId(),
                academicSpecializationLocale.getLanguageTag(),
                academicSpecializationLocale.getLevel(),
                academicSpecializationLocale.getName(),
                academicSpecializationLocale.getNameAbbreviated()
        );
    }

    public List<AcademicSpecializationResponse> getAllAcademicSpecializations() {
        return academicSpecializationRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AcademicSpecializationResponse getAcademicSpecializationById(Integer academicSpecializationId) {
        return convertToResponse(academicSpecializationRepository.findById(academicSpecializationId).orElseThrow());
    }

    public List<AcademicSpecializationLocaleResponse> getAllAcademicSpecializationsForLevelAndLanguage(String level, String language) {
        return academicSpecializationLocaleRepository.findAll().stream()
                .filter(academicSpecializationLocale -> academicSpecializationLocale.getLevel().equals(level) &&
                        academicSpecializationLocale.getLanguageTag().equals(language))
                .map(this::convertLocaleToResponse)
                .toList();
    }
}
