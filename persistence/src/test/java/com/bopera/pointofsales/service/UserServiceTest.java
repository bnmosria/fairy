package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.LoginUser;
import com.bopera.pointofsales.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        reset(userRepository);
    }

    @Test
    public void testGetUserListWhenUserRepositoryReturnsListThenReturnSameList() {
        // Arrange
        LoginUser user1 = new LoginUser(1, "user1");
        LoginUser user2 = new LoginUser(2, "user2");
        List<LoginUser> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findLoginUserList()).thenReturn(Optional.of(expectedUsers));

        // Act
        List<LoginUser> actualUsers = userService.getUserList();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findLoginUserList();
    }

    @Test
    public void testGetUserListWhenUserRepositoryReturnsEmptyOptionalThenThrowNoSuchElementException() {
        // Arrange
        when(userRepository.findLoginUserList()).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.getUserList());
        verify(userRepository, times(1)).findLoginUserList();
    }

    @Test
    public void testGetUserListWhenUsersExistThenReturnList() {
        // Arrange
        LoginUser user1 = new LoginUser(1, "user1");
        LoginUser user2 = new LoginUser(2, "user2");
        List<LoginUser> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findLoginUserList()).thenReturn(Optional.of(expectedUsers));

        // Act
        List<LoginUser> actualUsers = userService.getUserList();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findLoginUserList();
    }

    @Test
    public void testGetUserListWhenNoUsersExistThenThrowException() {
        // Arrange
        when(userRepository.findLoginUserList()).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.getUserList());
        verify(userRepository, times(1)).findLoginUserList();
    }
}
