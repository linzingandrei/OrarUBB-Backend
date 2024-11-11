package com.example.OrarUBB_Backend.domain;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formation {

    @Id
    @Column(name = "formation_id")
    private UUID formationId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_specialization_id", nullable = false)
    private AcademicSpecialization academicSpecialization;

    @Column(nullable = false, name = "formation_level")
    private int formationLevel;

    @Column(nullable = false)
    private String components;
}
