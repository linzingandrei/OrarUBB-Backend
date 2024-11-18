package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.AcademicRankLocale;
import com.example.OrarUBB_Backend.repository.AcademicRankLocaleRepository;
import com.example.OrarUBB_Backend.repository.AcademicRankRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AcademicRankLocaleService {
    private final AcademicRankLocaleRepository academicRankLocaleRepository;

    public AcademicRankLocaleService(AcademicRankLocaleRepository academicRankLocaleRepository) {
        this.academicRankLocaleRepository = academicRankLocaleRepository;
    }

    public List<AcademicRankLocale> getAllAcademicRankLocales() {
        return academicRankLocaleRepository.findAll();
    }

    public AcademicRankLocale getAcademicRankLocalesByAcademicRankId(Integer id, String languageTag) {
        return academicRankLocaleRepository.findByAcademicRank_AcademicRankIdAndAcademicRankLocaleKey_LanguageTag(id, languageTag);
    }
}
