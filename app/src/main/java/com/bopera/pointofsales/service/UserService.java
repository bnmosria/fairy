package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.bopera.pointofsales.exception.DuplicatedUserNameException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        Boolean existingUser = userRepository.existsByUsername(user.getUsername());

        if (existingUser != null) {
            throw new DuplicatedUserNameException();
        }

        userRepository.save(user);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
}
