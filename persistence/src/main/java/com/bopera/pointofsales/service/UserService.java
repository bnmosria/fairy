package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return userRepository.findLoginUserList().orElseThrow();
    }

}
