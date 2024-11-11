package com.example.OrarUBB_Backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "academic_rank")
public class AcademicRank {
    @Id
    private Integer academicRankId;

    private String rankName;

//    @OneToMany(mappedBy = "academicRank")
//    private Set<AcademicRankLocale> locales;
}
