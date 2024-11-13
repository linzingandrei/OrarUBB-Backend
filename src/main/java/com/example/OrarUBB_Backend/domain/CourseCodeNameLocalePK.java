package com.example.OrarUBB_Backend.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseCodeNameLocalePK implements Serializable {
    private int courseCodeNameId;
    private String languageTag;

    // Default constructor, getters, and setters if needed
}
