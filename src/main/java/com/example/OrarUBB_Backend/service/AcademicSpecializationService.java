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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                academicSpecializationLocale.getNameAbbreviated(),
                "1;"
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
        // Fetch raw data from repository
        List<Object[]> results = academicSpecializationRepository.findSpecializationsWithFormationCount(level, language);
        List<AcademicSpecializationLocaleResponse> response = new ArrayList<>();

        for (Object[] academicSpecialization : results) {
            StringBuilder years = new StringBuilder();
            for (int i = 1; i <= (long) academicSpecialization[3]; i++) {
                years.append(i).append(";");
            }
            response.add(new AcademicSpecializationLocaleResponse(
                    (Integer) academicSpecialization[0],        // academicSpecializationId
                    language,                // languageTag (passed to the method)
                    level,                   // level (passed to the method)
                    (String) academicSpecialization[1],         // internalName
                    (String) academicSpecialization[2],                      // nameAbbreviated (not provided, can be added later)
                    years.toString()   // formation count as a string
            ));
        }
        return response;
    }

    public List<AcademicSpecializationLocale> getAcademicSpecializationWithAbbrev(String abbreviation) {
        return academicSpecializationLocaleRepository.findByNameAbbreviated(abbreviation);
    }
}
