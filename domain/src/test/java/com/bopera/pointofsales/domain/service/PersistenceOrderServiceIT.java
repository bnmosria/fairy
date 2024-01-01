package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.entity.OrderEntity;
import com.bopera.pointofsales.persistence.entity.OrderItem;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableJpaRepositories("com.bopera.pointofsales.persistence.repository")
@EntityScan("com.bopera.pointofsales.persistence.entity")
class PersistenceOrderServiceIT {

    private OrderService persistenceOrderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @BeforeEach
    public void setup() {
        persistenceOrderService = new OrderService(ordersRepository);
    }

    @Test
    void shouldAddTheOrderItemsToTheOrderCorrectly() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);

        List<OrderItem> orderItems = new ArrayList<>(order.getOrderItems());
        assertEquals(1, orderItems.size());

        OrderItem orderItem = orderItems.get(0);
        assertEquals(order, orderItem.getOrder());
        assertEquals(menuItem, orderItem.getMenuItem());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(20), orderItem.getSubtotal());
    }

    @Test
    public void shouldAddMenuItemToOrderWhenNewMenuItem() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);
        assertEquals(BigDecimal.valueOf(20), order.getTotalAmount());
    }

    @Test
    public void shouldAddMenuItemToOrderWhenExistingMenuItemSameQuantity() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);
        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);

        assertEquals(1, order.getOrderItems().size());
        assertEquals(BigDecimal.valueOf(40), order.getTotalAmount());
    }

    @Test
    public void shouldAddMenuItemToOrderWhenExistingMenuItemDifferentQuantity() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        MenuItemEntity menuItem1 = new MenuItemEntity();

        menuItem.setPrice(BigDecimal.valueOf(10));
        menuItem1.setPrice(BigDecimal.valueOf(5));

        persistenceOrderService.addMenuItemToOrder(order, menuItem, 2);
        persistenceOrderService.addMenuItemToOrder(order, menuItem1, 3);

        assertEquals(2, order.getOrderItems().size());
        assertEquals(BigDecimal.valueOf(35), order.getTotalAmount());
    }
}
