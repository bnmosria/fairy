package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Boolean existsByUsername(@Param("userName") String userName);
}