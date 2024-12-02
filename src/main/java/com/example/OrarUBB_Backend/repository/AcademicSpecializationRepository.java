package com.example.OrarUBB_Backend.repository;
import com.example.OrarUBB_Backend.domain.AcademicSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface AcademicSpecializationRepository extends JpaRepository<AcademicSpecialization, Integer> {

    List<AcademicSpecialization> findByInternalName(String internalName);
}
