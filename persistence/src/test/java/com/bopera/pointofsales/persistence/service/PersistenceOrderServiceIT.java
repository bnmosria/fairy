package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import com.bopera.pointofsales.persistence.entity.Order;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@DataJpaTest
class PersistenceOrderServiceIT {

    @Autowired
    private OrdersRepository ordersRepository;

    private PersistenceOrderService persistenceOrderService;

    @BeforeEach
    void setup() {
        persistenceOrderService = new PersistenceOrderService(ordersRepository);
    }

    @Test
    void shouldAddTheOrderItemsToTheOrderCorrectly() {
        Order order = new Order();
        order.setStatus("ready");

        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(BigDecimal.valueOf(10));

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setPrice(BigDecimal.valueOf(15));

        persistenceOrderService.addMenuItemToOrder(order, menuItem, 2);
        persistenceOrderService.addMenuItemToOrder(order, menuItem1, 1);

        Assertions.assertEquals(BigDecimal.valueOf(35.0), order.getTotalAmount());
    }
}
