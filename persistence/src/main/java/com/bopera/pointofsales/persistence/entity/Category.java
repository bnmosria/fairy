package com.bopera.pointofsales.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<MenuItem> menuItems = new HashSet<>();

}
