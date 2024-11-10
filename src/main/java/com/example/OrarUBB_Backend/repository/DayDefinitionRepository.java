package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.DayDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DayDefinitionRepository extends JpaRepository<DayDefinition, UUID> {

    // Custom query method to find a DayDefinition by dayName
    Optional<DayDefinition> findByDayName(String dayName);
}
