package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.RoomTable;
import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.entity.OrderEntity;
import com.bopera.pointofsales.persistence.entity.OrderItemEntity;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void addMenuItemToOrder(OrderEntity order, MenuItemEntity menuItem, int quantity, RoomTable roomTable) {
        OrderItemEntity existingOrderItemEntity = getOrderItemByMenuItem(order, menuItem);

        if (existingOrderItemEntity != null) {
            int newQuantity = existingOrderItemEntity.getQuantity() + quantity;
            BigDecimal newSubtotal = menuItem.getPrice().multiply(BigDecimal.valueOf(newQuantity));

            existingOrderItemEntity.setQuantity(newQuantity);
            existingOrderItemEntity.setSubtotal(newSubtotal);
        } else {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(order);
            orderItemEntity.setMenuItem(menuItem);
            orderItemEntity.setQuantity(quantity);
            orderItemEntity.setSubtotal(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));

            order.getOrderItemEntities().add(orderItemEntity);
            menuItem.getOrderItemEntities().add(orderItemEntity);
        }

        order.setTotalAmount(getTotal(order));
        ordersRepository.save(order);
    }

    public void removeMenuItemFromOrder(OrderEntity order, MenuItemEntity menuItem) {
        OrderItemEntity orderItemEntity = getOrderItemByMenuItem(order, menuItem);

        if (orderItemEntity == null) {
            return;
        }

        int orderItemQuantity = orderItemEntity.getQuantity();

        if (orderItemQuantity > 1) {
            orderItemEntity.setQuantity(orderItemQuantity - 1);
            orderItemEntity.setSubtotal(
                menuItem.getPrice().multiply(
                    BigDecimal.valueOf(orderItemEntity.getQuantity())
                )
            );

            order.setTotalAmount(getTotal(order));
            ordersRepository.save(order);

            return;
        }

        order.getOrderItemEntities().remove(orderItemEntity);
        menuItem.getOrderItemEntities().remove(orderItemEntity);
        orderItemEntity.setOrder(null);
        orderItemEntity.setMenuItem(null);
        order.setTotalAmount(getTotal(order));

        ordersRepository.save(order);

    }

    private OrderItemEntity getOrderItemByMenuItem(OrderEntity order, MenuItemEntity menuItem) {
        return order.getOrderItemEntities().stream()
            .filter(orderItem -> orderItem.getMenuItem().equals(menuItem))
            .findFirst()
            .orElse(null);
    }

    private BigDecimal getTotal(OrderEntity order) {
        return order.getOrderItemEntities().stream()
            .map(OrderItemEntity::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
