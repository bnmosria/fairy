package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import com.bopera.pointofsales.domain.service.PersistenceUserService;
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
}
