package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersistenceMenuItemService {

    private final MenuItemsRepository menuItemsRepository;

    @Autowired
    public PersistenceMenuItemService(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemsRepository.save(menuItem);
    }


    public List<MenuItem> saveMenuItems(List<MenuItem> menuItems) {
        return menuItemsRepository.saveAll(menuItems);
    }

    public MenuItem updateMenuItem(MenuItem menuItem) {
        return menuItemsRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        menuItemsRepository.deleteById(id);
    }

    public List<MenuItem> findMenuItemByName(String menuItemName) {
        return menuItemsRepository.findByName(menuItemName);
    }

    public Optional<MenuItem> findMenuItemById(Long id) {
        return menuItemsRepository.findById(id);
    }

    public List<MenuItem> findAllMenuItems() {
        return menuItemsRepository.findAll();
    }

}
