package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.LoginUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<LoginUser, Integer> {

    @Query(value = "SELECT id, username FROM osp_user WHERE active = 1", nativeQuery = true)
    Optional<List<LoginUser>> findLoginUserList();
}