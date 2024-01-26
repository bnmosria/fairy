package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.entity.UserEntity;
import com.bopera.pointofsales.auth.model.UserInfo;
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

        UserEntity userInfo = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

        return UserInfo.builder()
                .name(userInfo.getUsername())
                .password(userInfo.getPassword())
                .authorities(buildAuthorities(userInfo))
                .build();
    }

    private List<GrantedAuthority> buildAuthorities(UserEntity userInfo) {

        return userInfo.getRoles().stream()
            .map(RoleEntity::getRoleName)
            .takeWhile(Predicate.not(String::isBlank))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

}
