package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

public class DayDefinitionAPIObject {

    private final UUID dayId;
    private final String dayName;

    public DayDefinitionAPIObject(UUID dayId, String dayName) {
        this.dayId = dayId;
        this.dayName = dayName;
    }

    public UUID getDayId() {
        return dayId;
    }

    public String getDayName() {
        return dayName;
    }
}