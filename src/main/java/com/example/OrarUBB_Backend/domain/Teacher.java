package com.example.OrarUBB_Backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    private UUID teacherId;

    @Column(name = "first_name")
    private String firstName;

    private String surname;

    @Column(name = "code_name")
    private String codeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_rank_id")
    private AcademicRank academicRank;



}
