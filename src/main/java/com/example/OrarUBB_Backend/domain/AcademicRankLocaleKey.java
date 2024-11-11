package com.example.OrarUBB_Backend.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AcademicRankLocaleKey implements Serializable {
    private Integer academicRankId;
    private String languageTag;
}
