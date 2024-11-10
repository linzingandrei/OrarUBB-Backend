package com.example.OrarUBB_Backend.domain;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassType {

    @Id
    @Column(name = "class_type_id")
    private UUID classTypeId;

    @Column(name = "class_type", nullable = false)
    private String classType; 
}
