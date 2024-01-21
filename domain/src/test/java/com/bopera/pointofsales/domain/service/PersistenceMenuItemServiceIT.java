package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.MenuItem;
import com.bopera.pointofsales.domain.BasePersistenceTest;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class PersistenceMenuItemServiceIT extends BasePersistenceTest {

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    private MenuItemService persistenceMenuItemService;

    @BeforeEach
    void setUp() {
        persistenceMenuItemService = new MenuItemService(menuItemsRepository);
    }

    @AfterEach
    void shutDown() {
        menuItemsRepository.deleteAll();
    }

    @Test
    void shouldSaveMenuItem() {
        MenuItem menuItem = MenuItem.builder()
            .name("Test Item")
            .price(BigDecimal.valueOf(10.99))
            .build();

        MenuItem savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        Assertions.assertNotNull(savedMenuItem.getId());
        Assertions.assertEquals("Test Item", savedMenuItem.getName());

    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItem menuItem = MenuItem.builder()
            .name("Test Item")
            .description("Test Item description")
            .price(BigDecimal.valueOf(10.99))
            .build();

        MenuItem savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        MenuItem updatedMenuItem = persistenceMenuItemService
            .findMenuItemById(savedMenuItem.getId())
            .map(menuItem1 -> {
                menuItem1.setName("Foo Item");
                return persistenceMenuItemService.updateMenuItem(menuItem1);
            }).orElseThrow();

        Assertions.assertEquals("Foo Item", updatedMenuItem.getName());
    }

    @Test
    void shouldFindMenuItemById() {
        MenuItem menuItem = MenuItem.builder()
            .name("Test Item")
            .description("Test Item description")
            .price(BigDecimal.valueOf(10.99))
            .build();

        persistenceMenuItemService.saveMenuItem(menuItem);

        List<MenuItem> foundMenuItem = persistenceMenuItemService
            .findMenuItemByName("Test Item");

        Assertions.assertNotNull(foundMenuItem.get(0));
        Assertions.assertEquals("Test Item", foundMenuItem.get(0).getName());
        Assertions.assertEquals("Test Item description", foundMenuItem.get(0).getDescription());
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItem menuItem1 = MenuItem.builder()
            .name("Item 1")
            .price(BigDecimal.valueOf(10.99))
            .build();

        MenuItem menuItem2 = MenuItem.builder()
            .name("Item 2")
            .price(BigDecimal.valueOf(15.99))
            .build();

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        persistenceMenuItemService.saveMenuItems(menuItems);

        List<MenuItem> foundMenuItems = persistenceMenuItemService.findAllMenuItems();

        Assertions.assertEquals(2, foundMenuItems.size());
    }
}
