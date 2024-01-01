package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.MenuItemEntity;

import java.util.List;

public interface MenuItemsRepository extends BaseRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByName(String menuItemName);
}
