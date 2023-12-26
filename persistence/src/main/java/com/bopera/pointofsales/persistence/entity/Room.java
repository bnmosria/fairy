package com.bopera.pointofsales.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    private int sorting;

    @OneToMany(mappedBy = "room")
    private Set<RoomTable> tables = new HashSet<>();

}
