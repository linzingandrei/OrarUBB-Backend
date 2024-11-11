package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserClassRelationAPIObject {
    private final String username;
    private final UUID classId;

    public UserClassRelationAPIObject(String username, UUID classId) {
        this.username = username;
        this.classId = classId;
    }
}
