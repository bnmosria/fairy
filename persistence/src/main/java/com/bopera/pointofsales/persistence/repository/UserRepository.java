package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE active = 1", nativeQuery = true)
    Optional<List<User>> findLoginUserList();

    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByUsername(String admin);
}
