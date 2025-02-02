package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.entity.UserEntity;
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

    private UserInfoService userInfoService;

    @BeforeEach
    public void setUp() {
        userInfoService = new UserInfoService(userRepository);
    }

    @Test
    public void shouldThrowsExceptionWhenUserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void ShouldLoadUserByUsername_WhenUserExists() {
        String username = "testUser";
        String password = "testPassword";
        RoleEntity role = new RoleEntity();
        role.setRoleName("ROLE_USER");

        UserEntity user = UserEntity.builder()
            .username(username)
            .password(password)
            .build();

        Set.of(role).forEach(user::addRole);

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
