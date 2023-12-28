package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class MenuItemsRepositoryTest {

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @Test
    void shouldSaveMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItem savedMenuItem = menuItemsRepository.save(menuItem);

        Assertions.assertNotNull(savedMenuItem.getId());
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItem savedMenuItem = menuItemsRepository.save(menuItem);

        savedMenuItem.setName("Updated Item");
        MenuItem updatedMenuItem = menuItemsRepository.save(savedMenuItem);

        Assertions.assertEquals("Updated Item", updatedMenuItem.getName());
    }

    @Test
    void shouldDeleteMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItem savedMenuItem = menuItemsRepository.save(menuItem);

        menuItemsRepository.delete(savedMenuItem);

        Optional<MenuItem> deletedMenuItem = menuItemsRepository.findById(savedMenuItem.getId());

        Assertions.assertFalse(deletedMenuItem.isPresent());
    }

    @Test
    void shouldFindByMenuItemName() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        menuItemsRepository.save(menuItem);

        List<MenuItem> foundItems = menuItemsRepository.findByName("Test Item");

        Assertions.assertEquals(1, foundItems.size());
        Assertions.assertEquals("Test Item", foundItems.get(0).getName());
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setName("Item 1");
        menuItem1.setPrice(BigDecimal.valueOf(10.99));

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setName("Item 2");
        menuItem2.setPrice(BigDecimal.valueOf(15.99));

        menuItemsRepository.save(menuItem1);
        menuItemsRepository.save(menuItem2);

        Assertions.assertEquals(2, menuItemsRepository.findAll().size());
    }
}
