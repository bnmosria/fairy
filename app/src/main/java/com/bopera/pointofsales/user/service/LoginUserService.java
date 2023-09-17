package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.auth.model.LoginUser;
import com.bopera.pointofsales.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginUserService {
    private final UserService userService;

    public LoginUserService(UserService userService) {
        this.userService = userService;
    }

    public List<LoginUser> getLoginUserList() {
        return userService.getUserList().stream().map(
                loginUser -> LoginUser.builder().name(loginUser.getUsername()
            ).build()).toList();
    }
}
