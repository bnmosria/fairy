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
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(quantity);
        orderItem.setSubtotal(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));

        order.getOrderItems().add(orderItem);
        menuItem.getOrderItems().add(orderItem);

        BigDecimal total = order.getTotalAmount().add(orderItem.getSubtotal());

        order.setTotalAmount(total);

        ordersRepository.save(order);
    }
}
