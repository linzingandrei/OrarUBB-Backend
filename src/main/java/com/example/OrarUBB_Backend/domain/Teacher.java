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
    private UUID teacherId;

    private String firstName;
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_rank_id")
    private AcademicRank academicRank;
}
