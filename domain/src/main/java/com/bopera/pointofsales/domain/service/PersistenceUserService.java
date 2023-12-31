package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.interfaces.UserServiceInterface;
import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersistenceUserService implements UserServiceInterface {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public PersistenceUserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void updatePassword(String currentPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder
            .getContext().getAuthentication();

        String username = authentication.getName();

        userRepository.findByUsername(username)
            .ifPresentOrElse(user -> {
                    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                        throw new HttpClientErrorException(
                            HttpStatus.BAD_REQUEST,
                            "Current password is incorrect"
                        );
                    }

                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                },
                () -> {
                    throw new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "The user was not found or does not exists"
                    );
                }
            );
    }
}
