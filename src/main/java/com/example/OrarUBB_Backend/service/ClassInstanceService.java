package com.example.OrarUBB_Backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.domain.ClassInstance;
import com.example.OrarUBB_Backend.repository.ClassInstanceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassInstanceService {
    private ClassInstanceRepository classInstanceRepository;

    public List<ClassInstance> getAllClassInstances() {
        return classInstanceRepository.findAll();
    }

    public ClassInstance getClassInstanceById(UUID classInstanceId) {
        Optional<ClassInstance> optionalClassInstace = classInstanceRepository.findById(classInstanceId);
        
        if (optionalClassInstace.isPresent()) {
            return optionalClassInstace.get();
        }

        log.info("Class instance with id: {} doesn't exist", classInstanceId);
        return null;
    }

    public ClassInstance saveClassInstance(ClassInstance classInstance) {
        ClassInstance savedClassInstance = classInstanceRepository.save(classInstance);

        log.info("Class instance with id: {} saved succesfully", classInstance.getClassId());
        return savedClassInstance;
    }

    public ClassInstance updateClassInstance(ClassInstance classInstance) {
        ClassInstance updatedClassInstance = classInstanceRepository.save(classInstance);

        log.info("Class instance with id: {} updated succesfully", classInstance.getClassId());

        return updatedClassInstance;
    }

    public void deleteClassInstance(UUID classInstanceId) {
        classInstanceRepository.deleteById(classInstanceId);

        log.info("Class instance with id: {} deleted succesfully", classInstanceId);
    }
}
