package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.exception.ResourceNotFoundException;
import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.domain.service.PersistenceUserService;
import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DbUserService implements UserService {
    private final PersistenceUserService persistenceUserService;
    private final PasswordEncoder passwordEncoder;

    public DbUserService(
        PersistenceUserService persistenceUserService,
        PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.persistenceUserService = persistenceUserService;
    }

    @Override
    public UserResponse findById(long id) throws ResourceNotFoundException {
        return persistenceUserService.findById(id)
            .map(UserResponse::new)
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public UserResponse create(CreateUser createUser) {
        UserDetails userDetails = UserDetails.builder()
            .username(createUser.getUsername())
            .password(passwordEncoder.encode(createUser.getPassword()))
            .active(createUser.getActive())
            .build();

        return Optional.of(persistenceUserService.save(userDetails))
            .map(UserResponse::new)
            .orElseThrow();
    }

    @Override
    public List<UserResponse> findAll() {
        return persistenceUserService.getUserList()
            .stream()
            .map(UserResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        persistenceUserService.delete(id);
    }

    @Override
    public UserResponse update(UpdateUser updateUser) throws ResourceNotFoundException {
        return persistenceUserService.findById(updateUser.getId())
            .map(user -> {
                user.setUsername(updateUser.getUsername());
                user.setActive(updateUser.getActive());

                persistenceUserService.save(user);

                return user;
            })
            .map(UserResponse::new)
            .orElseThrow();
    }

    @Override
    public void updatePassword(long id, String newPassword) throws ResourceNotFoundException {
        persistenceUserService.findById(id)
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                persistenceUserService.save(user);

                return user;
            })
            .map(UserResponse::new)
            .orElseThrow();
    }

}
