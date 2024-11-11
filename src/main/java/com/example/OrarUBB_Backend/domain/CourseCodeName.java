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
public class CourseCodeName {

    @Id
    @Column(name = "course_codename_id")
    private UUID courseCodeNameId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_name_abbreviaton", nullable = false)
    private String courseNameAbbreviation;
}
