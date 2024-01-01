package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.interfaces.MenuItemServiceInterface;
import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService implements MenuItemServiceInterface {

    private final MenuItemsRepository menuItemsRepository;

    @Autowired
    public MenuItemService(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public MenuItemEntity saveMenuItem(MenuItemEntity menuItem) {
        return menuItemsRepository.save(menuItem);
    }


    public List<MenuItemEntity> saveMenuItems(List<MenuItemEntity> menuItems) {
        return menuItemsRepository.saveAll(menuItems);
    }

    public MenuItemEntity updateMenuItem(MenuItemEntity menuItem) {
        return menuItemsRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        menuItemsRepository.deleteById(id);
    }

    public List<MenuItemEntity> findMenuItemByName(String menuItemName) {
        return menuItemsRepository.findByName(menuItemName);
    }

    public Optional<MenuItemEntity> findMenuItemById(Long id) {
        return menuItemsRepository.findById(id);
    }

    public List<MenuItemEntity> findAllMenuItems() {
        return menuItemsRepository.findAll();
    }

}
