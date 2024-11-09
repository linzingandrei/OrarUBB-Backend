package com.example.OrarUBB_Backend.repository;
import com.example.OrarUBB_Backend.domain.AcademicSpecializationLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AcademicSpecializationLocaleRepository
        extends JpaRepository<AcademicSpecializationLocale, AcademicSpecializationLocale.AcademicSpecializationLocaleId> {

    List<AcademicSpecializationLocale> findByLanguageTag(String languageTag);

    List<AcademicSpecializationLocale> findByLevel(String level);

    List<AcademicSpecializationLocale> findByName(String name);

    List<AcademicSpecializationLocale> findByNameAbbreviated(String nameAbbreviated);
}
