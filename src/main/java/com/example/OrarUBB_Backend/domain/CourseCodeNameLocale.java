package com.example.OrarUBB_Backend.domain;
import java.io.Serializable;
import java.util.UUID;

import com.example.OrarUBB_Backend.domain.CourseCodeNameLocale.CourseCodeNameLocaleId;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(CourseCodeNameLocaleId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCodeNameLocale {
    @Id
    @Column(name = "course_codename_id")
    private UUID courseCodeNameId;

    @Id
    @Column(name = "language_tag", nullable = false)
    private String languageTag;

    @Column(name = "course_name_locale",nullable = false)
    private String courseNameLocale;

    @Column(name = "course_name_abbreviaton_locale", nullable = false)
    private String courseNameAbbreviatonLocale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_codename_id",
            referencedColumnName = "course_codename_id",
            insertable = false,
            updatable = false
    )
    private CourseCodeName courseCodeName;


    @EqualsAndHashCode
    public class CourseCodeNameLocaleId implements Serializable {
        private UUID courseCodeNameId;
        private String languageTag;

        // Default constructor, getters, and setters if needed
    }
}
