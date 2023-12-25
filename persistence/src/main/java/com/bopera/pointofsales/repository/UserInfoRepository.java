package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends BaseRepository<User, Integer> {
	@Query(value = "SELECT * FROM users WHERE active = 1", nativeQuery = true)
	Optional<User> findByUsername(@Param("username") String username);
}
