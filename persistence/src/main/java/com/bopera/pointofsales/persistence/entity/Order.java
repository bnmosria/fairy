package com.bopera.pointofsales.persistence.entity;

import com.bopera.pointofsales.persistence.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RoomTable tableId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.valueOf(0.0);

    @Column(name = "timestamp")
    private Date timestamp;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

}
