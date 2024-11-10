package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.ClassTypeLocale;
import com.example.OrarUBB_Backend.domain.ClassTypeLocale.ClassTypeLocaleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassTypeLocaleRepository extends JpaRepository<ClassTypeLocale, ClassTypeLocaleId> {

    // Custom query to find a localized class type by classTypeId and languageTag
    Optional<ClassTypeLocale> findByClassTypeIdAndLanguageTag(UUID classTypeId, String languageTag);

    // Custom query to find all locales for a given classTypeId
    List<ClassTypeLocale> findAllByClassTypeId(UUID classTypeId);
}