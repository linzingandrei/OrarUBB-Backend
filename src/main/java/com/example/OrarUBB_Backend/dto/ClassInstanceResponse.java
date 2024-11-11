package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassInstanceResponse {
    private UUID classId;
    private int classDayId;
    private int startHour;
    private int endHour;
    private int frequency;
    private UUID roomId;
    private UUID formationId;
    private int classTypeId;
    private UUID courseInstanceId;
}
