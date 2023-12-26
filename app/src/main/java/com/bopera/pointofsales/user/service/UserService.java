package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(CreateUser createUser);

    List<UserResponse> findAll();

    UserResponse findById(long id);

    UserResponse update(UpdateUser updateUser);

    UserResponse updatePassword(long id, String updatePassword);

    void delete(long id);

}
