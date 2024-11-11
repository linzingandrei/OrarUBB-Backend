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
    private final AcademicRankRepository academicRankRepository;
    private final AcademicRankLocaleRepository academicRankLocaleRepository;

    public AcademicRankLocaleService(AcademicRankRepository academicRankRepository, AcademicRankLocaleRepository academicRankLocaleRepository) {
        this.academicRankLocaleRepository = academicRankLocaleRepository;
        this.academicRankRepository = academicRankRepository;
    }

    @PostConstruct
    public void init() {
        AcademicRankLocale rank1 = AcademicRankLocale.of(1, "ro-RO", "Profesor Universitar", "Prof.", academicRankRepository);
        academicRankLocaleRepository.save(rank1);
//        AcademicRankLocale rank1_eng = AcademicRankLocale.of(1, "en-GB", "Professor", "Prof.", academicRankRepository);
//        academicRankLocaleRepository.save(rank1_eng);
        AcademicRankLocale rank2 = AcademicRankLocale.of(2, "ro-RO", "Conferentiar Universitar", "Conf.", academicRankRepository);
        academicRankLocaleRepository.save(rank2);
        AcademicRankLocale rank2_eng = AcademicRankLocale.of(2, "en-GB", "Associate Professor", "Assoc.", academicRankRepository);
        academicRankLocaleRepository.save(rank2_eng);
        System.out.println(getAcademicRankLocalesByAcademicRankId(1, "ro-RO"));
//        System.out.println(getAcademicRankLocalesByAcademicRankId(1, "en-GB"));
        System.out.println(getAcademicRankLocalesByAcademicRankId(2, "ro-RO"));
        System.out.println(getAcademicRankLocalesByAcademicRankId(2, "en-GB"));
    }

    public List<AcademicRankLocale> getAllAcademicRankLocales() {
        return academicRankLocaleRepository.findAll();
    }

    public AcademicRankLocale getAcademicRankLocalesByAcademicRankId(Integer id, String languageTag) {
        return academicRankLocaleRepository.findByAcademicRank_AcademicRankIdAndAcademicRankLocaleKey_LanguageTag(id, languageTag);
    }
}
