package com.example.OrarUBB_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseCodeNameLocaleResponse {
    private UUID courseCodeNameId;
    private String languageTag;
    private String courseNameLocale;
    private String courseNameAbbreviationLocale;
}