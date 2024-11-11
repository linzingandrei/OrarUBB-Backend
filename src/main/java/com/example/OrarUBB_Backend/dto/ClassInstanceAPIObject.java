package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInstanceAPIObject {
    private final UUID classId;
    private final int classDayId;
    private final int startHour;
    private final int endHour;
    private final int frequency;
    private final UUID roomId;
    private final UUID formationId;
    private final int classTypeId;
    private final UUID courseInstanceId;

    public ClassInstanceAPIObject(UUID classId, int classDayId, int startHour, int endHour, int frequency, UUID roomId,
            UUID formationId, int classTypeId, UUID courseInstanceId) {
        this.classId = classId;
        this.classDayId = classDayId;
        this.startHour = startHour;
        this.endHour = endHour;
        this.frequency = frequency;
        this.roomId = roomId;
        this.formationId = formationId;
        this.classTypeId = classTypeId;
        this.courseInstanceId = courseInstanceId;
    }
}
