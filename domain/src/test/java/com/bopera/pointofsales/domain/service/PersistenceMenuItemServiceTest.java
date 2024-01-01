package com.bopera.pointofsales.domain.service;

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
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setId(2345L);
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        when(menuItemsRepository.save(any())).thenReturn(menuItem);

        MenuItemEntity savedMenuItem = persistenceMenuItemService.saveMenuItem(menuItem);

        Assertions.assertNotNull(savedMenuItem.getId());
        verify(menuItemsRepository, times(1)).save(menuItem);
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setId(1L);
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        when(menuItemsRepository.save(menuItem)).thenReturn(menuItem);

        MenuItemEntity updatedMenuItem = persistenceMenuItemService.updateMenuItem(menuItem);

        Assertions.assertEquals("Test Item", updatedMenuItem.getName());
        verify(menuItemsRepository, times(1)).save(menuItem);
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
        Long id = 1L;
        MenuItemEntity menuItem = new MenuItemEntity();
        menuItem.setId(id);
        menuItem.setName("Test Item");
        menuItem.setPrice(BigDecimal.valueOf(10.99));

        when(menuItemsRepository.findById(id)).thenReturn(Optional.of(menuItem));

        Optional<MenuItemEntity> foundMenuItem = persistenceMenuItemService.findMenuItemById(id);

        Assertions.assertTrue(foundMenuItem.isPresent());
        Assertions.assertEquals("Test Item", foundMenuItem.get().getName());
        verify(menuItemsRepository, times(1)).findById(id);
    }

    @Test
    void shouldFindAllMenuItems() {
        MenuItemEntity menuItem1 = new MenuItemEntity();
        menuItem1.setId(1L);
        menuItem1.setName("Item 1");
        menuItem1.setPrice(BigDecimal.valueOf(10.99));

        MenuItemEntity menuItem2 = new MenuItemEntity();
        menuItem2.setId(2L);
        menuItem2.setName("Item 2");
        menuItem2.setPrice(BigDecimal.valueOf(15.99));

        List<MenuItemEntity> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        when(menuItemsRepository.findAll()).thenReturn(menuItems);

        List<MenuItemEntity> foundMenuItems = persistenceMenuItemService.findAllMenuItems();

        Assertions.assertEquals(2, foundMenuItems.size());
        verify(menuItemsRepository, times(1)).findAll();
    }
}
