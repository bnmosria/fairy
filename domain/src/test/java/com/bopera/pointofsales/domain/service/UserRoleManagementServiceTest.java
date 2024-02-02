package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Role;
import com.bopera.pointofsales.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UserRoleManagementServiceTest {

    @Mock
    private PersistenceUserService persistenceUserService;

    private UserRoleManagementService userRoleManagementService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRoleManagementService = new UserRoleManagementService(persistenceUserService);
        user = User.builder().username("testuser").build();
        role = Role.builder().name("TestRole").build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(persistenceUserService);
    }

    @Test
    void ShouldAddRoleAndSaveUser_WhenRoleAddedToUser() {
        userRoleManagementService.addRoleToUser(user, role);

        verify(persistenceUserService, times(1)).save(user);
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void ShouldRemoveRoleAndSaveUser_WhenRoleRemovedFromUser() {
        user.addRole(role);

        userRoleManagementService.removeRoleFromUser(user, role);
        verify(persistenceUserService, times(1)).save(user);
        assertEquals(0, user.getRoles().size());
    }

    @Test
    void ShouldReturnUserRoles_WhenGetUserRolesCalled() {
        user.addRole(role);

        Set<Role> roles = userRoleManagementService.getUserRoles(user);
        assertEquals(1, roles.size());
        assertTrue(roles.contains(role));
    }
}
