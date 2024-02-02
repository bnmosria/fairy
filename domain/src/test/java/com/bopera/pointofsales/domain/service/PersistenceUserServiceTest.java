package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.exception.DuplicatedUserNameException;
import com.bopera.pointofsales.domain.model.User;
import com.bopera.pointofsales.persistence.entity.UserEntity;
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
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        List<UserEntity> returnedUsers = Arrays.asList(user1, user2);

        List<User> expectedUserDetails = Arrays.asList(
            User.builder().build().withRolesAndPermissions(user1),
            User.builder().build().withRolesAndPermissions(user2)
        );

        when(userRepository.findLoginUserList()).thenReturn(Optional.of(returnedUsers));

        List<User> actualUserDetails = userService.getUserList();

        assertEquals(expectedUserDetails, actualUserDetails);
        verify(userRepository, times(1)).findLoginUserList();
    }

    @Test
    public void ShouldThrowsDuplicatedUserNameException_WhenUserWithSameUsernameExists() {
        User userDetails = new User();
        userDetails.setUsername("existingUser");
        userDetails.setPassword("password");
        userDetails.setActive(1);

        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUserEntity));
        assertThrows(DuplicatedUserNameException.class, () -> userService.save(userDetails));

        verify(userRepository, times(1))
            .findByUsername("existingUser");
    }

    @Test
    public void shouldReturnAnEmptyListReturningAnExceptionWhenNoUsersExist() {
        when(userRepository.findLoginUserList()).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserList());
        verify(userRepository, times(1)).findLoginUserList();
    }
}
