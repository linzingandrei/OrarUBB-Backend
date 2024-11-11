package com.example.OrarUBB_Backend.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserClassRelationPK.class)
public class UserClassRelation {
    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "class_id")
    private UUID classId;

    @ManyToOne
    @JoinColumn(
        name = "class_id",
        referencedColumnName = "class_id"
    )
    private ClassInstance classInstance;
}
