package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
import java.util.Set;

@Data
@AllArgsConstructor
public class AcademicSpecializationResponse {
    private UUID academicSpecializationId;
    private String internalName;
    private Set<AcademicSpecializationLocaleResponse> locales;
}
