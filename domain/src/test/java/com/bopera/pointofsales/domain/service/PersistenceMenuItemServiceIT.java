package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@EnableJpaRepositories("com.bopera.pointofsales.persistence.repository")
@EntityScan("com.bopera.pointofsales.persistence.entity")
class PersistenceMenuItemServiceIT {

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    private MenuItemService persistenceMenuItemService;

    @BeforeEach
    void setUp() {
        persistenceMenuItemService = new MenuItemService(menuItemsRepository);
        menuItemsRepository.deleteAll();
    }

    @Test
    void shouldSaveMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        Assertions.assertNotNull(savedMenuItem.getId());
        Assertions.assertEquals("Test Item", savedMenuItem.getName());

    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setName("Test Item");
        menuItem.setDescription("Test Item description");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        MenuItemEntity updatedMenuItem = persistenceMenuItemService
            .findMenuItemById(savedMenuItem.getId())
            .map(menuItem1 -> {
                menuItem1.setName("Foo Item");
                return persistenceMenuItemService.updateMenuItem(menuItem1);
            }).orElseThrow();

        Assertions.assertEquals("Foo Item", updatedMenuItem.getName());
    }

    @Test
    void shouldFindMenuItemById() {
        MenuItemEntity menuItem = new MenuItemEntity();

        menuItem.setName("Test Item");
        menuItem.setDescription("Test Item description");
        menuItem.setPrice(BigDecimal.valueOf(10.99));
        persistenceMenuItemService.saveMenuItem(menuItem);

        List<MenuItemEntity> foundMenuItem = persistenceMenuItemService
            .findMenuItemByName("Test Item");

        Assertions.assertNotNull(foundMenuItem.get(0));
        Assertions.assertEquals("Test Item", foundMenuItem.get(0).getName());
        Assertions.assertEquals("Test Item description", foundMenuItem.get(0).getDescription());
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItemEntity menuItem1 = new MenuItemEntity();
        menuItem1.setName("Item 1");
        menuItem1.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity menuItem2 = new MenuItemEntity();
        menuItem2.setName("Item 2");
        menuItem2.setPrice(BigDecimal.valueOf(15.99));

        List<MenuItemEntity> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        persistenceMenuItemService.saveMenuItems(menuItems);

        List<MenuItemEntity> foundMenuItems = persistenceMenuItemService.findAllMenuItems();

        Assertions.assertEquals(2, foundMenuItems.size());

    }
}
