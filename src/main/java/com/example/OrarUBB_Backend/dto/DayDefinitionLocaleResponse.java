package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DayDefinitionLocaleResponse {
    private UUID dayId;
    private String languageTag;
    private String dayNameLocale;
}