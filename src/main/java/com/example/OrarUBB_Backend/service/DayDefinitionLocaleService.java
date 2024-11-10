package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.DayDefinitionLocale;
import com.example.OrarUBB_Backend.repository.DayDefinitionLocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DayDefinitionLocaleService {

    @Autowired
    private DayDefinitionLocaleRepository dayDefinitionLocaleRepository;

    public Optional<DayDefinitionLocale> getLocaleByDayIdAndLanguage(UUID dayId, String languageTag) {
        return dayDefinitionLocaleRepository.findByDayIdAndLanguageTag(dayId, languageTag);
    }

    public List<DayDefinitionLocale> getLocalesByDayId(UUID dayId) {
        return dayDefinitionLocaleRepository.findAllByDayId(dayId);
    }
}
