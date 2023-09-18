package com.bopera.pointofsales.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "osp_resttables")
public class RoomTables {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tableno", nullable = false)
    private String tableno;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "area")
    private Integer area;

    @Column(name = "active")
    private Integer active;

    @Column(name = "allowoutorder")
    private Integer allowoutorder;

    @Column(name = "removed")
    private Integer removed;

    @Column(name = "sorting")
    private Integer sorting;

    @ManyToOne
    @JoinColumn(name="roomid", nullable=false)
    private Room room;

}
