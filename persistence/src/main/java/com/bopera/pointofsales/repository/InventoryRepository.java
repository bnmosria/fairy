package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryRepository extends BaseRepository<Inventory, Long> {

}
