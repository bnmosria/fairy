package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<UserDetails> getUserList();

    void delete(Long id);

    UserDetails save(UserDetails userDetails);

    Optional<UserDetails> findById(long id);

    void updatePassword(String currentPassword, String newPassword);

}
