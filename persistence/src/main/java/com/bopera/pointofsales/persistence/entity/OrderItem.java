package com.bopera.pointofsales.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
