package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
	@Query(value = "select u.id, u.username, u.userpassword, r.name as role from osp_user u join osp_roles r where u.username = :username and u.roleid = r.id", nativeQuery = true)
	Optional<UserInfo> findByUsername(@Param("username") String username);
}
