package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClassTypeLocaleResponse {
    private UUID classTypeId;
    private String languageTag;
    private String classTypeLocale;
}