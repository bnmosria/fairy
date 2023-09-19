package com.bopera.pointofsales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "osp_room")
public class Room {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "roomname", nullable = false)
    private String roomname;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "printer")
    private Integer printer;

    @Column(name = "removed")
    private Integer removed;

    @Column(name = "sorting")
    private Integer sorting;

    @OneToMany(targetEntity=RoomTable.class, fetch = FetchType.LAZY, mappedBy="room")
    private List<RoomTable> roomTables;

}
