package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class CourseCodeNameLocaleAPIObject {

    private final UUID courseCodeNameId;
    private final String languageTag;
    private final String courseNameLocale;
    private final String courseNameAbbreviationLocale;

    public CourseCodeNameLocaleAPIObject(UUID courseCodeNameId, String languageTag, String courseNameLocale, String courseNameAbbreviationLocale) {
        this.courseCodeNameId = courseCodeNameId;
        this.languageTag = languageTag;
        this.courseNameLocale = courseNameLocale;
        this.courseNameAbbreviationLocale = courseNameAbbreviationLocale;
    }

    public UUID getCourseCodeNameId() {
        return courseCodeNameId;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public String getCourseNameLocale() {
        return courseNameLocale;
    }

    public String getCourseNameAbbreviationLocale() {
        return courseNameAbbreviationLocale;
    }
}