package com.example.OrarUBB_Backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OrarUBB_Backend.domain.ClassInstance;

@Repository
public interface ClassInstanceRepository extends JpaRepository<ClassInstance, UUID> {

}
