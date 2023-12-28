package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.MenuItem;

import java.util.List;

public interface MenuItemsRepository extends BaseRepository<MenuItem, Long> {
    List<MenuItem> findByName(String menuItemName);
}
