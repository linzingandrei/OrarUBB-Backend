package com.example.OrarUBB_Backend.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UserClassRelationPK implements Serializable {
    private String username;
    private UUID classId;

    public UserClassRelationPK(String username, UUID classId) {
        this.username = username;
        this.classId = classId;
    }
}