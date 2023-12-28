package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import com.bopera.pointofsales.persistence.entity.Order;
import com.bopera.pointofsales.persistence.entity.OrderItem;
import com.bopera.pointofsales.persistence.enums.OrderStatus;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersistenceOrderServiceTest {
    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private PersistenceOrderService persistenceOrderService;

    @Test
    void shouldAddTheOrderItemsToTheOrderCorrectly() {
        Order order = new Order();
        MenuItem menuItem = new MenuItem();
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

        verify(ordersRepository).save(order);
    }

    @Test
    public void shouldUpdateExistingOrderItemWhenExistingMenuItemSameQuantity() {
        Order order = new Order();
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);
        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);

        List<OrderItem> orderItems = new ArrayList<>(order.getOrderItems());

        assertEquals(1, orderItems.size());

        OrderItem orderItem = orderItems.get(0);
        assertEquals(order, orderItem.getOrder());
        assertEquals(menuItem, orderItem.getMenuItem());
        assertEquals(quantity * 2, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(40), orderItem.getSubtotal());

        verify(ordersRepository, times(2)).save(order);
    }

    @Test
    public void shouldAddNewOrderItemWhenExistingMenuItemDifferentQuantity() {
        Order order = new Order();
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(BigDecimal.valueOf(10));

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setPrice(BigDecimal.valueOf(5));
        int quantity = 2;
        int quantity1 = 3;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);
        persistenceOrderService.addMenuItemToOrder(order, menuItem1, quantity1);
        persistenceOrderService.addMenuItemToOrder(order, menuItem1, 1);

        List<OrderItem> orderItems = new ArrayList<>(order.getOrderItems());

        assertEquals(2, orderItems.size());

        OrderItem orderItem = orderItems.get(0);
        assertEquals(order, orderItem.getOrder());
        assertEquals(menuItem, orderItem.getMenuItem());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(20), orderItem.getSubtotal());

        OrderItem orderItem1 = orderItems.get(1);
        assertEquals(order, orderItem1.getOrder());
        assertEquals(menuItem1, orderItem1.getMenuItem());
        assertEquals(4, orderItem1.getQuantity());
        assertEquals(BigDecimal.valueOf(20), orderItem1.getSubtotal());

        verify(ordersRepository, times(3)).save(order);

        assertEquals(BigDecimal.valueOf(40), order.getTotalAmount());
    }

}
