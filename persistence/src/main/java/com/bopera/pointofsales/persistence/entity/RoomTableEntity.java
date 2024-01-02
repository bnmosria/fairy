package com.bopera.pointofsales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tables")
public class RoomTableEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false)
    private String name;

    @Column(name = "table_number")
    private String tableNumber;

    @Column(name = "active")
    private int active;

    @Column(name = "sorting")
    private int sorting;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

}
