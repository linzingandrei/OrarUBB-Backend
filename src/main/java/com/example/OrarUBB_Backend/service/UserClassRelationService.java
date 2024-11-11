package com.example.OrarUBB_Backend.service;

import org.springframework.stereotype.Service;

import com.example.OrarUBB_Backend.repository.UserClassRelationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserClassRelationService {
    private UserClassRelationRepository userClassRelationRepository;

}
