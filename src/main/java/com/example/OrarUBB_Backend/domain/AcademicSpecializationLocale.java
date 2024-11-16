package com.example.OrarUBB_Backend.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;
import java.io.Serializable;
import java.util.UUID;

@Entity
@IdClass(AcademicSpecializationLocale.AcademicSpecializationLocaleId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSpecializationLocale {

    @Id
    @Column(name = "academic_specialization_id")
    private int academicSpecializationId;

    @Id
    @Column(name = "language_tag")
    private String languageTag;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "name_abbreviated")
    private String nameAbbreviated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "academic_specialization_id",
            referencedColumnName = "academic_specialization_id",
            insertable = false,
            updatable = false
    )
    private AcademicSpecialization academicSpecialization;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcademicSpecializationLocaleId implements Serializable {
        private int academicSpecializationId;
        private String languageTag;

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AcademicSpecializationLocaleId that = (AcademicSpecializationLocaleId) o;
            return Objects.equals(academicSpecializationId, that.academicSpecializationId) &&
                    Objects.equals(languageTag, that.languageTag);
        }

        @Override public int hashCode() {
            return Objects.hash(academicSpecializationId, languageTag);
        }
    }
}
