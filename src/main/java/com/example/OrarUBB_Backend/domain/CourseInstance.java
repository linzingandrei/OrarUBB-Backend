package com.example.OrarUBB_Backend.domain;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstance {

    @Id
    @Column(name = "course_instance_id")
    private UUID courseInstanceId;

    @Column(name = "course_id", insertable=false, updatable=false)
    private UUID courseId;

    @ManyToOne()
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "course_codename_id"
    )
    private CourseCodeName courseCodeName;

    @Column(name = "course_code")
    private String courseCode;
}
