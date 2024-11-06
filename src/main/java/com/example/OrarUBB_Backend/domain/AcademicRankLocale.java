package com.example.OrarUBB_Backend.domain;

import com.example.OrarUBB_Backend.repository.AcademicRankRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "academic_rank_locale")
public class AcademicRankLocale {
    @EmbeddedId
    private AcademicRankLocaleKey academicRankLocaleKey;
    @ManyToOne
    @MapsId("academicRankId")
    @JoinColumn(name = "academic_rank_id")
    private AcademicRank academicRank;
    private String academicRankLocaleName;
    private String academicRankAbbreviationLocaleName;

    // factory method
    public static AcademicRankLocale of(Integer academicRankId,
                                                        String languageTag,
                                                        String academicRankLocaleName,
                                                        String academicRankAbbreviationLocaleName,
                                                        AcademicRankRepository academicRankRepository) {
        AcademicRank academicRank = academicRankRepository.findById(academicRankId)
                .orElseThrow(() -> new IllegalArgumentException("Academic rank with id " + academicRankId + " not found"));

        AcademicRankLocaleKey key = new AcademicRankLocaleKey(academicRankId, languageTag);

        return AcademicRankLocale.builder()
                .academicRankLocaleKey(key)
                .academicRank(academicRank)
                .academicRankLocaleName(academicRankLocaleName)
                .academicRankAbbreviationLocaleName(academicRankAbbreviationLocaleName)
                .build();
    }
}

