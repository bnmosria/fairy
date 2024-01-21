package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemServiceInterface {
    MenuItem saveMenuItem(MenuItem menuItem);

    List<MenuItem> saveMenuItems(List<MenuItem> menuItems);

    MenuItem updateMenuItem(MenuItem menuItem);

    void deleteMenuItem(Long id);

    List<MenuItem> findMenuItemByName(String menuItemName);

    Optional<MenuItem> findMenuItemById(Long id);

    List<MenuItem> findAllMenuItems();
}
