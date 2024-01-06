package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.UserEntity;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;


@ActiveProfiles("test")
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Container
    @ServiceConnection
    private static final CockroachContainer cockroachContainer = new CockroachContainer(
        DockerImageName.parse("cockroachdb/cockroach:v22.2.3")
    );

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        UserEntity user1 = UserEntity.builder()
            .username("admin1")
            .password("password1")
            .active(1)
            .build();
        UserEntity user2 = UserEntity.builder()
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
        Optional<List<UserEntity>> userList = userRepository.findLoginUserList();
        Assertions.assertTrue(userList.isPresent());
        Assertions.assertEquals(1, userList.get().size());
    }

    @Test
    void shouldReturnsAnUserByUsername() {
        boolean exists = userRepository.existsByUsername("admin1");
        Assertions.assertTrue(exists);
    }
}
