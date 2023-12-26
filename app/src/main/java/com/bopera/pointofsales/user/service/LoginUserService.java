package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import com.bopera.pointofsales.persistence.service.PersistenceUserService;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginUserService {
    private final PersistenceUserService userService;

    public LoginUserService(PersistenceUserService userService) {
        this.userService = userService;
    }

    public List<LoginUserResponse> getLoginUserList() {
        return userService.getUserList().stream().map(
                loginUser -> LoginUserResponse.builder().name(loginUser.getUsername()
            ).build()).toList();
    }

    public UserResponse update(UpdateUser updateUser) {
        return UserResponse.builder().build();
    }

}
