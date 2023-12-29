package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.domain.service.PersistenceUserService;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.persistence.repository.UserRepository;
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
public class PersistenceUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PersistenceUserService userService;

    @BeforeEach
    public void setUp() {
        reset(userRepository);
    }

    @Test
    public void shouldReturnsAnUserListWhenUserExists() {
        User user1 = new User();
        User user2 = new User();
        List<User> returnedUsers = Arrays.asList(user1, user2);

        List<UserDetails> expectedUserDetails = Arrays.asList(
            new UserDetails(user1),
            new UserDetails(user2)
        );

        when(userRepository.findLoginUserList()).thenReturn(Optional.of(returnedUsers));

        List<UserDetails> actualUserDetails = userService.getUserList();

        assertEquals(expectedUserDetails, actualUserDetails);
        verify(userRepository, times(1)).findLoginUserList();
    }

    @Test
    public void shouldReturnAnEmptyListReturningAnExceptionWhenNoUsersExist() {
        when(userRepository.findLoginUserList()).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserList());
        verify(userRepository, times(1)).findLoginUserList();
    }
}
