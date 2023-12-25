package com.bopera.pointofsales.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "config")
public class Config {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "setting")
    private String setting;

}
