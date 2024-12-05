package com.example.OrarUBB_Backend.repository;
import com.example.OrarUBB_Backend.domain.AcademicSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface AcademicSpecializationRepository extends JpaRepository<AcademicSpecialization, Integer> {

    List<AcademicSpecialization> findByInternalName(String internalName);

    @Query("SELECT a.academicSpecializationId, a.name, a.nameAbbreviated, COUNT(f.formationId) AS formationCount " +
            "FROM AcademicSpecializationLocale a " +
            "JOIN Formation f ON a.academicSpecializationId = f.academicSpecialization.academicSpecializationId " +
            "WHERE f.formationLevel = 1 and a.languageTag = :languageTag and a.level = :level " +
            "GROUP BY a.academicSpecializationId, a.name, a.nameAbbreviated " +
            "ORDER BY formationCount DESC")
    List<Object[]> findSpecializationsWithFormationCount(@Param("level") String level, @Param("languageTag") String languageTag);

}
