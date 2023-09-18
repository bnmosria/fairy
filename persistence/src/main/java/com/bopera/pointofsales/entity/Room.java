package com.bopera.pointofsales.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "osp_room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @OneToMany(mappedBy="room")
    private Set<RoomTables> roomTables;

}
