package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.ClassTypeLocale;
import com.example.OrarUBB_Backend.repository.ClassTypeLocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassTypeLocaleService {

    @Autowired
    private ClassTypeLocaleRepository classTypeLocaleRepository;

    public Optional<ClassTypeLocale> getClassTypeLocale(UUID classTypeId, String languageTag) {
        return classTypeLocaleRepository.findByClassTypeIdAndLanguageTag(classTypeId, languageTag);
    }

    public List<ClassTypeLocale> getLocalesByClassTypeId(UUID classTypeId) {
        return classTypeLocaleRepository.findAllByClassTypeId(classTypeId);
    }
}
