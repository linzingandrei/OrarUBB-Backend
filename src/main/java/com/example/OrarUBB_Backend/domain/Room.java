package com.example.OrarUBB_Backend.domain;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @Column(name = "room_id")
    private int roomId;

    @Column
    private String name;

    @Column
    private String address;
}
