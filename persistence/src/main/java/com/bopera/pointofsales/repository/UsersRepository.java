package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsersRepository extends BaseRepository<User, Long> {

}
