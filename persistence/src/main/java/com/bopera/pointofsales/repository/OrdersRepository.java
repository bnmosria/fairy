package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdersRepository extends BaseRepository<Order, Long> {

}
