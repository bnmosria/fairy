package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.LoginUser;
import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.exception.DuplicatedUserNameException;
import com.bopera.pointofsales.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LoginUser> getUserList() {
        return userRepository.findLoginUserList().orElseThrow();
    }
}
