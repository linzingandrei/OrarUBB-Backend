package com.example.OrarUBB_Backend.repository;

import com.example.OrarUBB_Backend.domain.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassTypeRepository extends JpaRepository<ClassType, Integer> {

    // Custom query to find a ClassType by its classType name
    Optional<ClassType> findByClassType(String classType);

    // Optional: Custom query to check if a ClassType exists by its classType name
    boolean existsByClassType(String classType);
}
