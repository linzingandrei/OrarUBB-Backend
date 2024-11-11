package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class TeacherAPIObject {

    private final UUID teacherId;
    private final String name;

    public TeacherAPIObject(UUID teacherId, String name) {
        this.teacherId = teacherId;
        this.name = name;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }
}
