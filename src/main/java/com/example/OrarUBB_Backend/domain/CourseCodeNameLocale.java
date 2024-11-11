package com.example.OrarUBB_Backend.domain;
import java.util.UUID;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(CourseCodeNameLocalePK.class)
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


}
