package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersistenceUserService {
    private final UserRepository userRepository;

    public PersistenceUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDetails> getUserList() {
        return userRepository.findLoginUserList()
            .map(
                users -> users.stream()
                    .map(UserDetails::new).collect(Collectors.toList())
            )
            .orElseThrow();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDetails save(UserDetails userDetails) {
        return new UserDetails(
            userRepository.save(
                User.builder()
                    .username(userDetails.getUsername())
                    .password(userDetails.getUsername())
                    .roles(userDetails.getRoles())
                    .active(userDetails.getActive())
                    .build()
            )
        );
    }

    public Optional<UserDetails> findById(long id) {
        return userRepository.findById(id)
            .map(UserDetails::new);
    }
}
