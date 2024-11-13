package com.example.OrarUBB_Backend.domain;
import java.io.Serializable;
import java.util.UUID;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(DayDefinitionLocalePK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayDefinitionLocale {

    @Id
    @Column(name = "day_definition_id", insertable = false, updatable = false)
    private int dayId;

    @Id
    @Column(name = "language_tag")
    private String languageTag;

    @Column(name = "day_name_locale", nullable=false)
    private String dayNameLocale;

    @ManyToOne()
    @JoinColumn(
            name = "day_definition_id",
            referencedColumnName = "day_definition_id"
    )
    private DayDefinition dayDefinition;
}
