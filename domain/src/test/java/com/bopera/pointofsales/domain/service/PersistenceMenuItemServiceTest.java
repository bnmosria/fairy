package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.MenuItem;
import com.bopera.pointofsales.persistence.entity.MenuItemEntity;
import com.bopera.pointofsales.persistence.repository.MenuItemsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersistenceMenuItemServiceTest {

    @Mock
    private MenuItemsRepository menuItemsRepository;

    @InjectMocks
    private MenuItemService persistenceMenuItemService;

    @Test
    void shouldSaveMenuItemAndReturnItem() {
        MenuItem menuItem = MenuItem.builder()
            .name("Test Item")
            .description("Test Item description")
            .price(BigDecimal.valueOf(10.99))
            .build();

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(2345L);
        menuItemEntity.setName("Test Item");
        menuItemEntity.setPrice(BigDecimal.valueOf(10.99));

        doReturn(menuItemEntity).when(menuItemsRepository).save(any());
        MenuItem savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        Assertions.assertEquals(2345L, savedMenuItem.getId());
        verify(menuItemsRepository, times(1)).save(any());
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItem menuItem = MenuItem.builder()
            .id(1L)
            .name("Test Item")
            .description("Test Item description")
            .price(BigDecimal.valueOf(10.99))
            .build();

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(1L);
        menuItemEntity.setName("Test Item");
        menuItemEntity.setPrice(BigDecimal.valueOf(10.99));


        when(menuItemsRepository.findById(1L)).thenReturn(Optional.of(menuItemEntity));
        when(menuItemsRepository.save(any())).thenReturn(menuItemEntity);
        MenuItem updatedMenuItem = persistenceMenuItemService.updateMenuItem(menuItem);

        Assertions.assertEquals("Test Item", updatedMenuItem.getName());
        verify(menuItemsRepository, times(1)).save(menuItemEntity);
    }

    @Test
    void shouldDeleteMenuItem() {
        Long id = 1L;

        doNothing().when(menuItemsRepository).deleteById(id);

        persistenceMenuItemService.deleteMenuItem(id);

        verify(menuItemsRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldFindMenuItemById() {
        MenuItem menuItem = MenuItem.builder()
            .id(1L)
            .name("Test Item 1")
            .price(BigDecimal.valueOf(10.99))
            .build();

        Long id = 1L;
        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(id);
        menuItemEntity.setName("Test Item 1");
        menuItemEntity.setPrice(BigDecimal.valueOf(10.99));

        when(menuItemsRepository.findById(id)).thenReturn(Optional.of(menuItemEntity));

        Optional<MenuItem> foundMenuItem = persistenceMenuItemService.findMenuItemById(id);

        Assertions.assertTrue(foundMenuItem.isPresent());
        Assertions.assertEquals("Test Item 1", foundMenuItem.get().getName());
        verify(menuItemsRepository, times(1)).findById(id);
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItemEntity menuItemEntity1 = new MenuItemEntity();
        menuItemEntity1.setId(1L);
        menuItemEntity1.setName("Item 1");
        menuItemEntity1.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity menuItemEntity2 = new MenuItemEntity();
        menuItemEntity2.setId(2L);
        menuItemEntity2.setName("Item 2");
        menuItemEntity2.setPrice(BigDecimal.valueOf(15.99));

        List<MenuItemEntity> menuItemEntities = new ArrayList<>();
        menuItemEntities.add(menuItemEntity1);
        menuItemEntities.add(menuItemEntity2);

        when(menuItemsRepository.findAll()).thenReturn(menuItemEntities);

        List<MenuItem> foundMenuItems = persistenceMenuItemService.findAllMenuItems();

        Assertions.assertEquals(2, foundMenuItems.size());
        Assertions.assertEquals("Item 1", foundMenuItems.get(0).getName());
        verify(menuItemsRepository, times(1)).findAll();
    }
}
