package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.entity.LoginUser;
import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.exception.DuplicatedUserNameException;
import com.bopera.pointofsales.repository.UserRepository;
import com.bopera.pointofsales.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoginUserService {
    private final UserService userService;

    public LoginUserService(UserService userService) {
        this.userService = userService;
    }

    public List<String> getLoginNames() {
        return userService.getUserList().stream().map(LoginUser::getUsername).toList();
    }
}
