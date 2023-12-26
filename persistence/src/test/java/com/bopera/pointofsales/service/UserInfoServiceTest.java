package com.bopera.pointofsales.service;

import com.bopera.pointofsales.persistence.repository.UserInfoRepository;
import com.bopera.pointofsales.persistence.service.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    private UserInfoService userInfoService;

    @BeforeEach
    public void setUp() {
        userInfoService = new UserInfoService(userInfoRepository);
    }

    @Test
    public void shouldLoadUserByUsernameWhenUserNotFoundThenThrowsException() {
        String username = "testUser";
        when(userInfoRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(username));
        verify(userInfoRepository, times(1)).findByUsername(username);
    }
}
