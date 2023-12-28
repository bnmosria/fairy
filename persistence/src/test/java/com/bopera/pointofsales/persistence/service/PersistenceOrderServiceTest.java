package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import com.bopera.pointofsales.persistence.entity.Order;
import com.bopera.pointofsales.persistence.entity.OrderItem;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersistenceOrderServiceTest {
    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private PersistenceOrderService persistenceOrderService;

    @Test
    void testAddMenuItemToOrder() {
        Order order = new Order();
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(BigDecimal.valueOf(10.5));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity);

        List<OrderItem> orderItems = new ArrayList<>(order.getOrderItems());
        assertEquals(1, orderItems.size());

        OrderItem orderItem = orderItems.get(0);
        assertEquals(order, orderItem.getOrder());
        assertEquals(menuItem, orderItem.getMenuItem());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(21.0), orderItem.getSubtotal());

        verify(ordersRepository).save(order);
    }
}
