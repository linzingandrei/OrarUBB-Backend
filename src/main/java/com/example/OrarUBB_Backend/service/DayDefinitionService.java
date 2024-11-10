package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.DayDefinition;
import com.example.OrarUBB_Backend.repository.DayDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DayDefinitionService {

    @Autowired
    private DayDefinitionRepository dayDefinitionRepository;

    public Optional<DayDefinition> getDayDefinitionByName(String dayName) {
        return dayDefinitionRepository.findByDayName(dayName);
    }
}
