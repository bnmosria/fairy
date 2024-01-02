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

    @Column(name = "table_number", nullable = false)
    private String tableNumber;

    @Column(name = "table_name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Integer active;

    @Column(name = "sorting", nullable = false)
    private Integer sorting;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

}
