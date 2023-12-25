package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuItemsRepository extends BaseRepository<MenuItem, Long> {

}
