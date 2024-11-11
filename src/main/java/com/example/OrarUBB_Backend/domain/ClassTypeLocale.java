package com.example.OrarUBB_Backend.domain;
import java.io.Serializable;
import java.util.UUID;

import com.example.OrarUBB_Backend.domain.ClassTypeLocale.ClassTypeLocaleId;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(ClassTypeLocaleId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeLocale {

    @Id
    @Column(name = "class_type_id")
    private UUID classTypeId;

    @Id
    @Column(name = "language_tag")
    private String languageTag;

    @Column(name = "class_type_locale", nullable = false)
    private String classTypeLocale; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "class_type_id",
            referencedColumnName = "class_type_id",
            insertable = false,
            updatable = false
    )
    private ClassType classType;


    @EqualsAndHashCode
    public static class ClassTypeLocaleId implements Serializable {
        private UUID classTypeId;
        private String languageTag;

        // Default constructor, getters, and setters (if needed)
    }
}
