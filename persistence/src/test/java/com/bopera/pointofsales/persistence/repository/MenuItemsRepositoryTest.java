package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuItemsRepositoryTest {

    @Container
    @ServiceConnection
    private static final CockroachContainer cockroachContainer = new CockroachContainer(
        DockerImageName.parse("cockroachdb/cockroach:v22.2.3")
    );

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @Test
    void shouldSaveMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity savedMenuItem = menuItemsRepository.save(menuItem);

        Assertions.assertNotNull(savedMenuItem.getId());
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity savedMenuItem = menuItemsRepository.save(menuItem);

        savedMenuItem.setName("Updated Item");
        MenuItemEntity updatedMenuItem = menuItemsRepository.save(savedMenuItem);

        Assertions.assertEquals("Updated Item", updatedMenuItem.getName());
    }

    @Test
    void shouldDeleteMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity savedMenuItem = menuItemsRepository.save(menuItem);

        menuItemsRepository.delete(savedMenuItem);

        Optional<MenuItemEntity> deletedMenuItem = menuItemsRepository.findById(savedMenuItem.getId());

        Assertions.assertFalse(deletedMenuItem.isPresent());
    }

    @Test
    void shouldFindByMenuItemName() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        menuItemsRepository.save(menuItem);

        List<MenuItemEntity> foundItems = menuItemsRepository.findByName("Test Item");

        Assertions.assertEquals(1, foundItems.size());
        Assertions.assertEquals("Test Item", foundItems.get(0).getName());
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItemEntity menuItem1 = new MenuItemEntity();
        menuItem1.setName("Item 1");
        menuItem1.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity menuItem2 = new MenuItemEntity();
        menuItem2.setName("Item 2");
        menuItem2.setPrice(BigDecimal.valueOf(15.99));

        menuItemsRepository.save(menuItem1);
        menuItemsRepository.save(menuItem2);

        Assertions.assertEquals(2, menuItemsRepository.findAll().size());
    }
}
