package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.RoomTable;
import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.entity.OrderEntity;
import com.bopera.pointofsales.persistence.entity.OrderItemEntity;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private OrderService persistenceOrderService;

    @Test
    void testRemoveMenuItemFromOrderWhenQuantityGreaterThanOneThenRemoveOne() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        RoomTable roomTable = RoomTable.builder().id(1L).build();
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);
        persistenceOrderService.removeMenuItemFromOrder(order, menuItem);

        List<OrderItemEntity> orderItemEntities = new ArrayList<>(order.getOrderItemEntities());
        assertEquals(1, orderItemEntities.size());

        OrderItemEntity orderItemEntity = orderItemEntities.get(0);
        assertEquals(order, orderItemEntity.getOrder());
        assertEquals(menuItem, orderItemEntity.getMenuItem());
        assertEquals(quantity - 1, orderItemEntity.getQuantity());
        assertEquals(BigDecimal.valueOf(10), orderItemEntity.getSubtotal());

        verify(ordersRepository, times(2)).save(order);
    }

    @Test
    void testRemoveMenuItemFromOrderWhenQuantityIsOneThenRemoveItem() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        RoomTable roomTable = RoomTable.builder().id(1L).build();
        menuItem.setPrice(BigDecimal.valueOf(10));
        int quantity = 1;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);
        persistenceOrderService.removeMenuItemFromOrder(order, menuItem);

        assertEquals(0, order.getOrderItemEntities().size());

        verify(ordersRepository, times(2)).save(order);
    }

    @Test
    void testRemoveMenuItemFromOrderWhenMenuItemNotInOrderThenDoNothing() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));

        persistenceOrderService.removeMenuItemFromOrder(order, menuItem);

        List<OrderItemEntity> orderItemEntities = new ArrayList<>(order.getOrderItemEntities());
        assertEquals(0, orderItemEntities.size());

        verify(ordersRepository, times(0)).save(order);
    }

    @Test
    void shouldAddTheOrderItemsToTheOrderCorrectly() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        RoomTable roomTable = RoomTable.builder().id(1L).build();
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

        verify(ordersRepository).save(order);
    }

    @Test
    public void shouldUpdateExistingOrderItemWhenExistingMenuItemSameQuantity() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));
        RoomTable roomTable = RoomTable.builder().id(1L).build();
        int quantity = 2;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);
        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);

        List<OrderItemEntity> orderItemEntities = new ArrayList<>(order.getOrderItemEntities());

        assertEquals(1, orderItemEntities.size());

        OrderItemEntity orderItemEntity = orderItemEntities.get(0);
        assertEquals(order, orderItemEntity.getOrder());
        assertEquals(menuItem, orderItemEntity.getMenuItem());
        assertEquals(quantity * 2, orderItemEntity.getQuantity());
        assertEquals(BigDecimal.valueOf(40), orderItemEntity.getSubtotal());

        verify(ordersRepository, times(2)).save(order);
    }

    @Test
    public void shouldAddNewOrderItemWhenExistingMenuItemDifferentQuantity() {
        OrderEntity order = new OrderEntity();
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setPrice(BigDecimal.valueOf(10));

        MenuItemEntity menuItem1 = new MenuItemEntity();
        menuItem1.setPrice(BigDecimal.valueOf(5));
        RoomTable roomTable = RoomTable.builder().id(1L).build();
        int quantity = 2;
        int quantity1 = 3;

        persistenceOrderService.addMenuItemToOrder(order, menuItem, quantity, roomTable);

        persistenceOrderService.addMenuItemToOrder(order, menuItem1, quantity1, roomTable);
        persistenceOrderService.addMenuItemToOrder(order, menuItem1, 1, roomTable);

        assertEquals(2, order.getOrderItemEntities().size());

        OrderItemEntity orderItemEntity = order.getOrderItemEntities().get(0);
        assertEquals(order, orderItemEntity.getOrder());
        assertEquals(menuItem, orderItemEntity.getMenuItem());
        assertEquals(quantity, orderItemEntity.getQuantity());
        assertEquals(BigDecimal.valueOf(20), orderItemEntity.getSubtotal());

        OrderItemEntity orderItemEntity1 = order.getOrderItemEntities().get(1);
        assertEquals(order, orderItemEntity1.getOrder());
        assertEquals(menuItem1, orderItemEntity1.getMenuItem());
        assertEquals(4, orderItemEntity1.getQuantity());
        assertEquals(BigDecimal.valueOf(20), orderItemEntity1.getSubtotal());

        verify(ordersRepository, times(3)).save(order);

        assertEquals(BigDecimal.valueOf(40), order.getTotalAmount());
    }
}
