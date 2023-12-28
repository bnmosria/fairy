package com.bopera.pointofsales.persistence.service;

import com.bopera.pointofsales.persistence.entity.Role;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersistenceUserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    private PersistenceUserInfoService userInfoService;

    @BeforeEach
    public void setUp() {
        userInfoService = new PersistenceUserInfoService(userRepository);
    }

    @Test
    public void shouldThrowsExceptionWhenUserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldLoadUserByUsernameWithExistingUser() {
        String username = "testUser";
        String password = "testPassword";
        Role role = new Role();
        role.setRoleName("ROLE_USER");

        User user = User.builder().username(username)
            .password(password)
            .roles(Set.of(role))
            .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userInfoService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(role.getRoleName(), userDetails.getAuthorities().iterator().next().getAuthority());

        verify(userRepository, times(1)).findByUsername(username);
    }

}
