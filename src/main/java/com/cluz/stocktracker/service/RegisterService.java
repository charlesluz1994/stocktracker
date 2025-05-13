package com.cluz.stocktracker.service;

import com.cluz.stocktracker.entity.User;
import com.cluz.stocktracker.entity.UserRole;
import com.cluz.stocktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterService {

	private final UserRepository userRepository;

	public void registerUser(User user, boolean isAdmin) {
		var newUser = User.builder()
				.name(user.getName())
				.email(user.getEmail())
				.userRoles(isAdmin ? List.of(UserRole.ADMIN, UserRole.USER) : List.of(UserRole.USER))
				.password(new BCryptPasswordEncoder().encode(user.getPassword()))
				.build();

		userRepository.save(newUser);
	}
}
