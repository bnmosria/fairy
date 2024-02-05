package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.exception.DuplicatedRoleException;
import com.bopera.pointofsales.domain.exception.DuplicatedUserNameException;
import com.bopera.pointofsales.domain.interfaces.UserServiceInterface;
import com.bopera.pointofsales.domain.model.User;
import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.entity.UserEntity;
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

    public List<User> getUserList() {
        return userRepository.findLoginUserList()
            .map(
                users -> users.stream()
                    .map(userEntity -> User.builder().build()
                        .withRolesAndPermissions(userEntity)
                    )
                    .collect(Collectors.toList())
            )
            .orElseThrow();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {

        Optional<UserEntity> existingUser = userRepository
            .findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new DuplicatedUserNameException(
                "User with the same username already exists"
            );
        }

        UserEntity userEntity = UserEntity.builder()
            .username(user.getUsername())
            .password(passwordEncoder.encode(user.getPassword()))
            .active(user.getActive())
            .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);

        User mappedUser = User.builder()
            .id(savedUserEntity.getId())
            .username(savedUserEntity.getUsername())
            .active(savedUserEntity.getActive())
            .build();

        return mappedUser.withRolesAndPermissions(savedUserEntity);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id)
            .map(userEntity -> User.builder().build()
                .withRolesAndPermissions(userEntity)
            );
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
