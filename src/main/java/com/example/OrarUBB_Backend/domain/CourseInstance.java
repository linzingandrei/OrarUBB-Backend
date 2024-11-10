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
    @Column(name = "course_instance_id", updatable = false, nullable = false)
    private UUID courseInstanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "course_codename_id",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private CourseCodeName courseCodeName;

    @Column(name = "course_code")
    private String courseCode;
}
