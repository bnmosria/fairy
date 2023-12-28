package com.bopera.pointofsales.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.valueOf(0.0);

    @Column(name = "timestamp")
    private Date timestamp;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();

}
