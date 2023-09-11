package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.UserInfo;
import com.bopera.pointofsales.repository.UserInfoRepository;
import com.bopera.pointofsales.model.UserInfoDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

	private final UserInfoRepository repository;

	public UserInfoService(UserInfoRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userDetail = repository.findByUsername(username);

		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

}
