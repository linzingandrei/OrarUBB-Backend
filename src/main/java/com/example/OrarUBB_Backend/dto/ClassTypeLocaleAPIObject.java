package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class ClassTypeLocaleAPIObject {

    private final UUID classTypeId;
    private final String languageTag;
    private final String classTypeLocale;

    public ClassTypeLocaleAPIObject(UUID classTypeId, String languageTag, String classTypeLocale) {
        this.classTypeId = classTypeId;
        this.languageTag = languageTag;
        this.classTypeLocale = classTypeLocale;
    }

    public UUID getClassTypeId() {
        return classTypeId;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public String getClassTypeLocale() {
        return classTypeLocale;
    }
}