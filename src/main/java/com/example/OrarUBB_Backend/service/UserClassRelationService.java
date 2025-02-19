package com.example.OrarUBB_Backend.service;

import com.example.OrarUBB_Backend.domain.UserClassRelation;
import com.example.OrarUBB_Backend.dto.ClassInstanceResponse;
import com.example.OrarUBB_Backend.repository.ClassInstanceRepository;
import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.repository.UserClassRelationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserClassRelationService {
    private final UserClassRelationRepository userClassRelationRepository;
    private final ClassInstanceRepository classInstanceRepository;
    private final ClassInstanceService classInstanceService;

    public List<ClassInstanceResponse> getClassesForUser(String username, String language) {
        List<ClassInstanceResponse> results = new ArrayList<>();
        List<UserClassRelation> userClasses = userClassRelationRepository.findByUsername(username);
        for (UserClassRelation userClassRelation : userClasses) {
            List<Object[]> classInstance = classInstanceRepository.findClassInstanceByClassId(userClassRelation.getClassId(), language);
            ClassInstanceResponse classInstanceResponse = classInstanceService.mapObjectsToClassInstanceResponse(classInstance).getLast();
            results.add(classInstanceResponse);

        }
        return results;
    }

    public void mapGroupCoursesToUser(String userId, String groupCode) {
        userClassRelationRepository.removeUserClassRelationByUsername(userId);
        List<ClassInstanceResponse> classes = classInstanceService.getClassesForGroup(groupCode, "ro-RO");
        for (ClassInstanceResponse classInstanceResponse : classes) {
            userClassRelationRepository.addUserClassRelation(userId, classInstanceResponse.getClassId());
        }
    }
}