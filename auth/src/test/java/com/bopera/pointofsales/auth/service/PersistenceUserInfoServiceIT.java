package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.entity.UserEntity;
import com.bopera.pointofsales.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaRepositories("com.bopera.pointofsales.persistence.repository")
@EntityScan("com.bopera.pointofsales.persistence.entity")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersistenceUserInfoServiceIT {

    @Autowired
    private UserRepository userRepository;

    private UserInfoService userInfoService;

    @BeforeEach
    void setUp() {
        userInfoService = new UserInfoService(userRepository);
    }

    @AfterEach
    void shutDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturnsUserDetailsByUsernameWithExistingUser() {
        String username = "user";
        String password = "password";
        RoleEntity role = new RoleEntity();
        role.setRoleName("ROLE_USER");

        UserEntity user = UserEntity.builder().username(username)
            .password(password)
            .active(1)
            .build();

        Set.of(role).forEach(user::addRole);

        userRepository.save(user);

        UserDetails userDetails = userInfoService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(role.getRoleName(), userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void shouldThrowsUsernameNotFoundExceptionWithNonExistingUser() {
        String username = "nonExistingUser";
        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(username));
    }
}
