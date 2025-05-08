package com.cluz.stocktracker.service;

import com.cluz.stocktracker.entity.User;
import com.cluz.stocktracker.entity.UserRole;
import com.cluz.stocktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username);
	}

	public void addUser(User user, boolean isAdmin) {
		var newUser = User.builder()
				.name(user.getName())
				.email(user.getEmail())
				.userRoles(isAdmin ? List.of(UserRole.ADMIN, UserRole.USER) : List.of(UserRole.USER))
				.password(new BCryptPasswordEncoder().encode(user.getPassword()))
				.build();

		userRepository.save(newUser);
	}
}
