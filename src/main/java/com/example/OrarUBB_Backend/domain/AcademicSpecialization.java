package com.example.OrarUBB_Backend.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSpecialization {

    @Id
    @Column(name = "academic_specialization_id")
    private int academicSpecializationId;

    @Column(nullable = false, name = "internal_name")
    private String internalName;

    @OneToMany(
            mappedBy = "academicSpecialization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AcademicSpecializationLocale> locales;

    public AcademicSpecializationLocale getLocaleByTag(String languageTag) {
        for (AcademicSpecializationLocale locale : locales) {
            if (locale.getLanguageTag().equals(languageTag)) {
                return locale;
            }
        }
        return null;
    }
}
