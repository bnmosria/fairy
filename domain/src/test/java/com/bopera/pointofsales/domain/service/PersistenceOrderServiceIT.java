package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.BasePersistenceTest;
import com.bopera.pointofsales.domain.model.RoomTable;
import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.entity.OrderEntity;
import com.bopera.pointofsales.persistence.entity.OrderItemEntity;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PersistenceOrderServiceIT extends BasePersistenceTest {

    private OrderService persistenceOrderService;

    @Autowired
    private OrdersRepository ordersRepository;

    private RoomTable roomTable;

    @BeforeEach
    public void setup() {
        persistenceOrderService = new OrderService(ordersRepository);

        this.roomTable = RoomTable.builder().id(1L).build();
    }

    @AfterEach
    void shutDown() {
        ordersRepository.deleteAll();
    }

    @Test
    void shouldAddTheOrderItemsToTheOrderCorrectly() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);

        List<OrderItemEntity> orderItemEntities = new ArrayList<>(order.getOrderItemEntities());
        assertEquals(1, orderItemEntities.size());

        OrderItemEntity orderItemEntity = orderItemEntities.get(0);
        assertEquals(order, orderItemEntity.getOrder());
        assertEquals(menuItem, orderItemEntity.getMenuItem());
        assertEquals(quantity, orderItemEntity.getQuantity());
        assertEquals(BigDecimal.valueOf(20), orderItemEntity.getSubtotal());
    }

    @Test
    public void shouldAddMenuItemToOrderWhenNewMenuItem() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);
        assertEquals(BigDecimal.valueOf(20), order.getTotalAmount());
    }

    @Test
    public void shouldAddMenuItemToOrderWhenExistingMenuItemSameQuantity() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);
        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);

        assertEquals(1, order.getOrderItemEntities().size());
        assertEquals(BigDecimal.valueOf(40), order.getTotalAmount());
    }

    @Test
    public void shouldAddMenuItemToOrderWhenExistingMenuItemDifferentQuantity() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        MenuItemEntity menuItem1 = new MenuItemEntity();

        menuItem.setPrice(BigDecimal.valueOf(10));
        menuItem1.setPrice(BigDecimal.valueOf(5));

        persistenceOrderService.addMenuItemToOrder(order, menuItem, 2, roomTable);
        persistenceOrderService.addMenuItemToOrder(order, menuItem1, 3, roomTable);

        assertEquals(2, order.getOrderItemEntities().size());
        assertEquals(BigDecimal.valueOf(35), order.getTotalAmount());
    }
}
