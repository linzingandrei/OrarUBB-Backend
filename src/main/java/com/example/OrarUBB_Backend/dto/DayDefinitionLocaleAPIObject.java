package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class DayDefinitionLocaleAPIObject {

    private final UUID dayId;
    private final String languageTag;
    private final String dayNameLocale;

    public DayDefinitionLocaleAPIObject(UUID dayId, String languageTag, String dayNameLocale) {
        this.dayId = dayId;
        this.languageTag = languageTag;
        this.dayNameLocale = dayNameLocale;
    }

    public UUID getDayId() {
        return dayId;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public String getDayNameLocale() {
        return dayNameLocale;
    }
}