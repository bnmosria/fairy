package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<User> getUserList();

    void delete(Long id);

    User save(User userDetails);

    Optional<User> findById(long id);

    void updatePassword(String currentPassword, String newPassword);

}
