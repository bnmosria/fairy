package com.bopera.pointofsales.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name", nullable = false)
    private String permissionName;

}
