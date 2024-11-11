package com.example.OrarUBB_Backend.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClassInstance {
    @Id
    @Column(name = "class_id")
    private UUID classId;

    @ManyToOne
    @JoinColumn(
        name = "class_day_id",
        referencedColumnName = "day_definition_id"
    )
    private DayDefinition dayDefinition; 

    @Column(name = "start_hour", nullable = false)
    private int startHour;

    @Column(name = "end_hour", nullable = false)
    private int endHour;

    @Column(name = "frequency", nullable = false)
    private int frequency;

    @ManyToOne
    @JoinColumn(
        name = "room_id",
        referencedColumnName = "room_id"
    )
    private Room room;

    @ManyToOne
    @JoinColumn(
        name = "formation_id",
        referencedColumnName = "formation_id"
    )
    private Formation formation;

    @ManyToOne
    @JoinColumn(
        name = "class_type_id",
        referencedColumnName = "class_type_id"
    )
    private ClassType classType;

    @ManyToOne
    @JoinColumn(
        name = "course_instance_id",
        referencedColumnName = "course_instance_id"
    )
    private CourseInstance courseInstance;

    @ManyToOne
    @JoinColumn(
        name = "teacher_id",
        referencedColumnName = "teacher_id"
    )
    private Teacher teacher;
}
