package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserClassRelationResponse {
    private String username;
    private UUID classId;
}
