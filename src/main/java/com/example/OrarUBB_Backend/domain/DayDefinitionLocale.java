package com.example.OrarUBB_Backend.domain;
import java.io.Serializable;
import java.util.UUID;

import com.example.OrarUBB_Backend.domain.DayDefinitionLocale.DayDefinitionLocaleId;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(DayDefinitionLocaleId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayDefinitionLocale {

    @Id
    @Column(name = "day_definition_id")
    private UUID dayId;

    @Id
    @Column(name = "language_tag")
    private String languageTag;

    @Column(name = "day_name_locale", nullable=false)
    private String dayNameLocale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "day_definition_id",
            referencedColumnName = "day_definition_id",
            insertable = false,
            updatable = false
    )
    private DayDefinition dayDefinition;

@EqualsAndHashCode
public static class DayDefinitionLocaleId implements Serializable {
    private UUID dayId;
    private String languageTag;
}

}
