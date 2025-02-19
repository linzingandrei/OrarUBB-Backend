package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClassInstanceRequest {
    private final UUID classId;
    private final int classDayId;
    private final int startHour;
    private final int endHour;
    private final int frequency;
    private final UUID roomId;
    private final UUID formationId;
    private final int classTypeId;
    private final UUID courseInstanceId;
    private final UUID teacherId;
}
