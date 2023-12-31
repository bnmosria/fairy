package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.persistence.entity.Role;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.domain.model.UserInfoDetails;
import com.bopera.pointofsales.persistence.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;

    public UserInfoService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userInfo = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

        return UserInfoDetails.builder()
                .name(userInfo.getUsername())
                .password(userInfo.getPassword())
                .authorities(buildAuthorities(userInfo))
                .build();
    }

    private List<GrantedAuthority> buildAuthorities(User userInfo) {

        return userInfo.getRoles().stream()
            .map(Role::getRoleName)
            .takeWhile(Predicate.not(String::isBlank))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

}
