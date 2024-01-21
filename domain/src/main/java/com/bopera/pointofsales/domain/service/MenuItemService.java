package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.interfaces.MenuItemServiceInterface;
import com.bopera.pointofsales.domain.model.MenuItem;
import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemService implements MenuItemServiceInterface {

    private final MenuItemsRepository menuItemsRepository;

    @Autowired
    public MenuItemService(MenuItemsRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public MenuItem saveMenuItem(MenuItem menuItem) {
        return buildMenuItemFromEntity(
            menuItemsRepository.save(
                buildEntityFromMenuItem(menuItem)
            )
        );
    }

    public List<MenuItem> saveMenuItems(List<MenuItem> menuItems) {
        return menuItemsRepository
            .saveAll(
                menuItems.stream().map(
                    this::buildEntityFromMenuItem
                ).collect(Collectors.toList())
            ).stream()
            .map(
                menuItemEntity -> MenuItem.builder().build()
            ).collect(Collectors.toList());
    }

    public MenuItem updateMenuItem(MenuItem menuItem) {
        return menuItemsRepository.findById(menuItem.getId())
            .map(menuItemEntity -> {
                menuItemEntity.setDescription(menuItem.getDescription());
                menuItemEntity.setDescription(menuItem.getName());

                menuItemsRepository.save(menuItemEntity);

                return menuItem;
            })
            .orElseThrow();
    }

    public void deleteMenuItem(Long id) {
        menuItemsRepository.deleteById(id);
    }

    public List<MenuItem> findMenuItemByName(String menuItemName) {
        return menuItemsRepository.findByName(menuItemName)
            .stream()
            .map(this::buildMenuItemFromEntity)
            .collect(Collectors.toList());
    }

    public Optional<MenuItem> findMenuItemById(Long id) {
        return menuItemsRepository.findById(id)
            .map(this::buildMenuItemFromEntity);
    }

    public List<MenuItem> findAllMenuItems() {
        return menuItemsRepository.findAll()
            .stream()
            .map(this::buildMenuItemFromEntity)
            .collect(Collectors.toList());
    }


    private MenuItemEntity buildEntityFromMenuItem(MenuItem menuItem) {
        return MenuItemEntity.builder()
            .id(menuItem.getId())
            .name(menuItem.getName())
            .description(menuItem.getDescription())
            .price(menuItem.getPrice())
            .build();
    }

    private MenuItem buildMenuItemFromEntity(MenuItemEntity menuItemEntity) {
        return MenuItem.builder()
            .id(menuItemEntity.getId())
            .name(menuItemEntity.getName())
            .description(menuItemEntity.getDescription())
            .price(menuItemEntity.getPrice())
            .build();
    }
}
