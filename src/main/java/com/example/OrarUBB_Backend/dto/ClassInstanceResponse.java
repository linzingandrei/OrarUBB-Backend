package com.example.OrarUBB_Backend.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassInstanceResponse {
    private final UUID classId; // direct din class_instance
    private final String classDay; // c
    private final int startHour; // direct din class_instance
    private final int endHour; // direct din class_instance
    private final int frequency; // direct din class instance
    private final String room;
    private final String formation;
    private final String classType;
    private final String courseInstanceCode;
    private final String courseInstanceName;
    private final String teacher;
}
/*
course instance code + name => course_instance_id => course_instance_table => courese_code_name => course_code-Name_locale + limba(ro-RO)
classDay = > cday_definition+. day definition_locale(id + language)X
room = > room_table -> nameX
formation => formation_table => academic_specialization => academic_specialization_locale(id+lang) -> name X
class_type => class_type_table -> class_type X
teacher_id => teacher => academic_rang_locale(id+lang) -> academic_rank_abrev X
 */