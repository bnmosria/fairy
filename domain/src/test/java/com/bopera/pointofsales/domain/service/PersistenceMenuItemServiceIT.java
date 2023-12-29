package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
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

    private PersistenceMenuItemService persistenceMenuItemService;

    @BeforeEach
    void setUp() {
        persistenceMenuItemService = new PersistenceMenuItemService(menuItemsRepository);
        menuItemsRepository.deleteAll();
    }

    @Test
    void shouldSaveMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItem savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        Assertions.assertNotNull(savedMenuItem.getId());
        Assertions.assertEquals("Test Item", savedMenuItem.getName());

    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test Item");
        menuItem.setDescription("Test Item description");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

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
        MenuItem menuItem = new MenuItem();

        menuItem.setName("Test Item");
        menuItem.setDescription("Test Item description");
        menuItem.setPrice(BigDecimal.valueOf(10.99));
        persistenceMenuItemService.saveMenuItem(menuItem);

        List<MenuItem> foundMenuItem = persistenceMenuItemService
            .findMenuItemByName("Test Item");

        Assertions.assertNotNull(foundMenuItem.get(0));
        Assertions.assertEquals("Test Item", foundMenuItem.get(0).getName());
        Assertions.assertEquals("Test Item description", foundMenuItem.get(0).getDescription());
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setName("Item 1");
        menuItem1.setPrice(BigDecimal.valueOf(10.99));

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setName("Item 2");
        menuItem2.setPrice(BigDecimal.valueOf(15.99));

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        persistenceMenuItemService.saveMenuItems(menuItems);

        List<MenuItem> foundMenuItems = persistenceMenuItemService.findAllMenuItems();

        Assertions.assertEquals(2, foundMenuItems.size());

    }
}
