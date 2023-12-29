package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.exception.ResourceNotFoundException;
import com.bopera.pointofsales.domain.service.PersistenceUserService;
import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbUserServiceTest {

    @Mock
    private PersistenceUserService persistenceUserService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DbUserService dbUserService;

    @Test
    void shouldReturnsExistingUser() throws ResourceNotFoundException {
        long userId = 1L;
        UserDetails user = UserDetails.builder()
            .id(userId).active(0).build();

        when(persistenceUserService.findById(userId)).thenReturn(Optional.of(user));

        UserResponse userResponse = dbUserService.findById(userId);

        assertNotNull(userResponse);
        assertEquals(userId, userResponse.getId());
    }

    @Test
    void shouldThrowsResourceNotFoundExceptionWhenUserNotFound() {
        long userId = 1L;
        when(persistenceUserService.findById(userId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> dbUserService.findById(userId));
    }

    @Test
    void shouldCreateAndReturnsTheCreatedUser() {
        CreateUser createUser = new CreateUser();
        createUser.setUsername("testuser");
        createUser.setPassword("password");
        createUser.setActive(0);

        UserDetails newUser = UserDetails.builder().id(234L)
            .username("testuser")
            .password("encodedPassword")
            .active(0).build();

        when(passwordEncoder.encode(createUser.getPassword())).thenReturn("encodedPassword");
        when(persistenceUserService.save(any())).thenReturn(newUser);

        UserResponse userResponse = dbUserService.create(createUser);

        assertNotNull(userResponse);
        assertEquals(newUser.getUsername(), userResponse.getUserName());
    }

    @Test
    void shouldReturnsListOfUsers() {
        List<UserDetails> userList = List.of(
            UserDetails.builder().id(1L).active(0).build(),
            UserDetails.builder().id(2L).active(1).build()
        );

        when(persistenceUserService.getUserList()).thenReturn(userList);

        List<UserResponse> userResponses = dbUserService.findAll();
        assertNotNull(userResponses);
        assertEquals(userList.size(), userResponses.size());
    }

    @Test
    void shouldVerifyDeleteUserCall() {
        long userId = 1L;

        dbUserService.delete(userId);
        verify(persistenceUserService, times(1)).delete(userId);
    }

    @Test
    void shouldReturnsUpdatedUserResponse() throws ResourceNotFoundException {
        long userId = 1L;
        UpdateUser updateUser = new UpdateUser();
        updateUser.setId(userId);
        updateUser.setUsername("updatedUsername");
        updateUser.setActive(1);

        UserDetails existingUser = UserDetails.builder().id(userId)
            .active(1).username("updatedUsername")
            .build();

        when(persistenceUserService.findById(userId)).thenReturn(Optional.of(existingUser));
        when(persistenceUserService.save(existingUser)).thenReturn(existingUser);

        UserResponse userResponse = dbUserService.update(updateUser);

        assertNotNull(userResponse);
        assertEquals(updateUser.getUsername(), userResponse.getUserName());
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserAndUserNotFound() {
        long userId = 1L;
        UpdateUser updateUser = new UpdateUser();
        updateUser.setId(userId);
        updateUser.setUsername("updatedUsername");

        when(persistenceUserService.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> dbUserService.update(updateUser));
    }

    @Test
    void shouldUpdatePasswordForExistingUser() throws ResourceNotFoundException {
        long userId = 1L;
        String newPassword = "newPassword";

        UserDetails existingUser = UserDetails.builder().id(userId)
            .active(1).build();

        when(persistenceUserService.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        dbUserService.updatePassword(userId, newPassword);

        verify(persistenceUserService, times(1))
            .save(existingUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePasswordAndUserNotFound() {
        long userId = 1L;
        String newPassword = "newPassword";

        when(persistenceUserService.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dbUserService.updatePassword(userId, newPassword));
    }
}
