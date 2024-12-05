package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AcademicSpecializationLocaleResponse {
    private int academicSpecializationId;
    private String languageTag;
    private String level;
    private String name;
    private String nameAbbreviated;
    private String years;
}
