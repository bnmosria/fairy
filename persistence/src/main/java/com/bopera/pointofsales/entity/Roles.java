package com.bopera.pointofsales.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "osp_roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_admin", nullable = false)
    private Boolean admin;

    @Column(name = "right_waiter", nullable = false)
    private Integer rightWaiter;

    @Column(name = "right_kitchen", nullable = false)
    private Integer rightKitchen;

    @Column(name = "right_bar", nullable = false)
    private Integer rightBar;

    @Column(name = "right_supply", nullable = false)
    private Integer rightSupply;

    @Column(name = "right_paydesk", nullable = false)
    private Integer rightPaydesk;

    @Column(name = "right_statistics", nullable = false)
    private Integer rightStatistics;

    @Column(name = "right_bill", nullable = false)
    private Integer rightBill;

    @Column(name = "right_products", nullable = false)
    private Integer rightProducts;

    @Column(name = "right_manager", nullable = false)
    private Integer rightManager;

    @Column(name = "right_closing", nullable = false)
    private Integer rightClosing;

    @Column(name = "right_dash", nullable = false)
    private Integer rightDash;

    @Column(name = "right_timetracking")
    private Integer rightTimetracking;

    @Column(name = "right_tasks")
    private Integer rightTasks;

    @Column(name = "right_tasksmanagement")
    private Integer rightTasksmanagement;

    @Column(name = "right_timemanager")
    private Integer rightTimemanager;

    @Column(name = "right_reservation", nullable = false)
    private Integer rightReservation;

    @Column(name = "right_rating", nullable = false)
    private Integer rightRating;

    @Column(name = "right_changeprice", nullable = false)
    private Integer rightChangeprice;

    @Column(name = "right_customers", nullable = false)
    private Integer rightCustomers;

    @Column(name = "right_pickups")
    private Integer rightPickups;

    @Column(name = "right_cashop")
    private Integer rightCashop;

    @Column(name = "right_delivery")
    private Integer rightDelivery;

    @Column(name = "right_customersview", nullable = false)
    private Integer rightCustomersview;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

}
