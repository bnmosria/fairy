package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE active = 1", nativeQuery = true)
    Optional<List<User>> findLoginUserList();

    boolean existsByUsername(String admin);
}
