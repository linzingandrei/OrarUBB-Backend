package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.DayDefinitionLocale;
import com.example.OrarUBB_Backend.domain.DayDefinitionLocalePK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DayDefinitionLocaleRepository extends JpaRepository<DayDefinitionLocale, DayDefinitionLocalePK> {

    // Custom query to find by dayId and languageTag
    public DayDefinitionLocale findByDayIdAndLanguageTag(int dayId, String languageTag);

    // Custom query to find all locales by dayId
    List<DayDefinitionLocale> findAllByDayId(int dayId);
}
