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

    @Column(name = "class_day_id", insertable=false, updatable=false)
    private int dayId;

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

    @Column(name = "room_id", insertable=false, updatable=false)
    private int roomId;

    @ManyToOne
    @JoinColumn(
        name = "room_id",
        referencedColumnName = "room_id"
    )
    private Room room;

    @Column(name = "formation_id", insertable=false, updatable=false)
    private UUID formationId;

    @ManyToOne
    @JoinColumn(
        name = "formation_id",
        referencedColumnName = "formation_id"
    )
    private Formation formation;

    @Column(name = "class_type_id", insertable = false, updatable = false)
    private int classTypeId;

    @ManyToOne
    @JoinColumn(
        name = "class_type_id",
        referencedColumnName = "class_type_id"
    )
    private ClassType classType;

    @Column(name = "course_instance_id", insertable = false, updatable = false)
    private UUID courseInstanceId;

    @ManyToOne
    @JoinColumn(
        name = "course_instance_id",
        referencedColumnName = "course_instance_id"
    )
    private CourseInstance courseInstance;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private UUID teacherId;

    @ManyToOne
    @JoinColumn(
        name = "teacher_id",
        referencedColumnName = "teacher_id"
    )
    private Teacher teacher;
}
