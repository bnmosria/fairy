package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;


@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
            .username("admin1")
            .password("password1")
            .active(1)
            .build();
        User user2 = User.builder()
            .username("admin2")
            .password("password2")
            .active(0)
            .build();

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();
    }

    @Test
    void shouldFindTheLoginUsersOfActiveUsers() {
        Optional<List<User>> userList = userRepository.findLoginUserList();
        Assertions.assertTrue(userList.isPresent());
        Assertions.assertEquals(1, userList.get().size());
    }

    @Test
    void shouldReturnsAnUserByUsername() {
        boolean exists = userRepository.existsByUsername("admin1");
        Assertions.assertTrue(exists);
    }
}
