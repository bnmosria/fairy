package com.bopera.pointofsales.user.service;

import com.bopera.pointofsales.exception.ResourceNotFoundException;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.persistence.service.PersistenceUserService;
import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        User newUser = new User();

        newUser.setUsername(createUser.getUsername());
        newUser.setActive(1);
        newUser.setPassword(passwordEncoder.encode(createUser.getPassword()));

        return new UserResponse(
            persistenceUserService.save(newUser)
        );
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
                persistenceUserService.save(user);

                return user;
            })
            .map(UserResponse::new)
            .orElseThrow();
    }

    @Override
    public UserResponse updatePassword(long id, String newPassword) throws ResourceNotFoundException {
        return persistenceUserService.findById(id)
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                persistenceUserService.save(user);

                return user;
            })
            .map(UserResponse::new)
            .orElseThrow();
    }

}
