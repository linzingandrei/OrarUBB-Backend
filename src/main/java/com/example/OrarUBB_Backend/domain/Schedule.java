package com.example.OrarUBB_Backend.domain;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    private int scheduleId;

    @Column(name = "user_id", insertable=false, updatable=false)
    private String userId;

    @ManyToOne()
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "user_id"
    )
    private User user;

    @Column(name = "class_id", nullable = true)
    private String classId;

    @Column(name = "class_day", nullable = true)
    private String classDay;

    @Column(name = "start_time", nullable = true)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = true)
    private LocalTime endTime;

    @Column(name = "frequency", nullable = true)
    private Integer frequency;

    @Column(name = "room_name", nullable = true)
    private String roomName;

    @Column(name = "formation", nullable = true)
    private String formation;

    @Column(name = "class_type", nullable = true)
    private String classType;

    @Column(name = "course_instance_name", nullable = true)
    private String courseInstanceName;

    @Column(name = "teacher_name", nullable = true)
    private String teacherName;

    @Override
    public String toString() {
        return "Schedule [scheduleId=" + scheduleId + ", userId=" + userId + ", user=" + user + ", classId=" + classId
                + ", classDay=" + classDay + ", startTime=" + startTime + ", endTime=" + endTime + ", frequency="
                + frequency + ", roomName=" + roomName + ", formation=" + formation + ", classType=" + classType
                + ", courseInstanceName=" + courseInstanceName + ", teacherName=" + teacherName + "]";
    }
}