package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import com.bopera.pointofsales.persistence.entity.Order;
import com.bopera.pointofsales.persistence.entity.OrderItem;
import com.bopera.pointofsales.persistence.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PersistenceOrderService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public PersistenceOrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void addMenuItemToOrder(Order order, MenuItem menuItem, int quantity) {
        OrderItem existingOrderItem = getOrderItemByMenuItem(order, menuItem);

        if (existingOrderItem != null) {
            int newQuantity = existingOrderItem.getQuantity() + quantity;
            BigDecimal newSubtotal = menuItem.getPrice().multiply(BigDecimal.valueOf(newQuantity));

            existingOrderItem.setQuantity(newQuantity);
            existingOrderItem.setSubtotal(newSubtotal);
        } else {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));

            order.getOrderItems().add(orderItem);
            menuItem.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(getTotal(order));
        ordersRepository.save(order);
    }

    public void removeMenuItemFromOrder(Order order, MenuItem menuItem) {
        OrderItem orderItem = getOrderItemByMenuItem(order, menuItem);

        if (orderItem == null) {
            return;
        }

        int orderItemQuantity = orderItem.getQuantity();

        if (orderItemQuantity > 1) {
            orderItem.setQuantity(orderItemQuantity - 1);
            orderItem.setSubtotal(
                menuItem.getPrice().multiply(
                    BigDecimal.valueOf(orderItem.getQuantity())
                )
            );

            order.setTotalAmount(getTotal(order));
            ordersRepository.save(order);

            return;
        }

        order.getOrderItems().remove(orderItem);
        menuItem.getOrderItems().remove(orderItem);
        orderItem.setOrder(null);
        orderItem.setMenuItem(null);
        order.setTotalAmount(getTotal(order));

        ordersRepository.save(order);

    }

    private OrderItem getOrderItemByMenuItem(Order order, MenuItem menuItem) {
        return order.getOrderItems().stream()
            .filter(orderItem -> orderItem.getMenuItem().equals(menuItem))
            .findFirst()
            .orElse(null);
    }

    private BigDecimal getTotal(Order order) {
        return order.getOrderItems().stream()
            .map(OrderItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
