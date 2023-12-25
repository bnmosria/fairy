package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemsRepository extends BaseRepository<OrderItem, Long> {

}
