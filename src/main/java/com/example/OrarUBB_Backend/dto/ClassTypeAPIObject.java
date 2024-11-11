package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class ClassTypeAPIObject {

    private final UUID classTypeId;
    private final String classType;

    public ClassTypeAPIObject(UUID classTypeId, String classType) {
        this.classTypeId = classTypeId;
        this.classType = classType;
    }

    public UUID getClassTypeId() {
        return classTypeId;
    }

    public String getClassType() {
        return classType;
    }
}
