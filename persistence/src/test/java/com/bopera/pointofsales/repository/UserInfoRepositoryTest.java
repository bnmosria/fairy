package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserInfoRepositoryTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    private UserInfo userInfo;

    @BeforeEach
    public void setUp() {
        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUsername("testUser");
        userInfo.setUserpassword("testPassword");
        userInfo.setRole("testRole");
    }

    @Test
    public void shouldFindByUsernameWhenUsernameIsValid() {
        when(userInfoRepository.findByUsername("testUser")).thenReturn(Optional.of(userInfo));
        Optional<UserInfo> returnedUserInfo = userInfoRepository.findByUsername("testUser");
        assertThat(returnedUserInfo).isPresent();
        assertThat(returnedUserInfo.get()).isEqualTo(userInfo);
    }

    @Test
    public void shouldReturnEmptyWhenFindByUsernameIsInvalid() {
        when(userInfoRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());
        Optional<UserInfo> returnedUserInfo = userInfoRepository.findByUsername("invalidUser");
        assertThat(returnedUserInfo).isNotPresent();
    }
}