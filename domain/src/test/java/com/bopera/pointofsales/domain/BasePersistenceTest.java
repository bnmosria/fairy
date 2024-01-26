package com.bopera.pointofsales.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EnableJpaRepositories("com.bopera.pointofsales.persistence.repository")
@EntityScan("com.bopera.pointofsales.persistence.entity")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BasePersistenceTest {

}
