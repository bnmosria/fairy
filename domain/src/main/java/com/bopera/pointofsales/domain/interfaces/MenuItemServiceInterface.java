package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.persistence.entity.MenuItemEntity;

import java.util.List;
import java.util.Optional;

public interface MenuItemServiceInterface {
    MenuItemEntity saveMenuItem(MenuItemEntity menuItem);

    List<MenuItemEntity> saveMenuItems(List<MenuItemEntity> menuItems);

    MenuItemEntity updateMenuItem(MenuItemEntity menuItem);

    void deleteMenuItem(Long id);

    List<MenuItemEntity> findMenuItemByName(String menuItemName);

    Optional<MenuItemEntity> findMenuItemById(Long id);

    List<MenuItemEntity> findAllMenuItems();
}
