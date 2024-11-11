package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.ClassType;
import com.example.OrarUBB_Backend.repository.ClassTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassTypeService {

    @Autowired
    private ClassTypeRepository classTypeRepository;

    public Optional<ClassType> getClassTypeByName(String classType) {
        return classTypeRepository.findByClassType(classType);
    }

    public boolean classTypeExists(String classType) {
        return classTypeRepository.existsByClassType(classType);
    }

    public ClassType saveClassType(ClassType classType) {
        return classTypeRepository.save(classType);
    }
}
