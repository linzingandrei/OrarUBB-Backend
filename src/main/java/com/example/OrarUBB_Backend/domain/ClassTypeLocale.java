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
@IdClass(ClassTypeLocalePK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassTypeLocale {

    @Id
    @Column(name = "class_type_id", insertable = false, updatable = false)
    private UUID classTypeId;

    @Id
    @Column(name = "language_tag")
    private String languageTag;

    @Column(name = "class_type_locale", insertable = false, updatable = false)
    private String classTypeLocale; 

    @ManyToOne()
    @JoinColumn(
            name = "class_type_id",
            referencedColumnName = "class_type_id"
    )
    private ClassType classType;


}
