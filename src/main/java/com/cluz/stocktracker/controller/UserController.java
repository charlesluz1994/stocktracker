package com.cluz.stocktracker.controller;

import com.cluz.stocktracker.controller.request.AuthRequest;
import com.cluz.stocktracker.controller.request.UserRegisterRequest;
import com.cluz.stocktracker.controller.response.AuthResponse;
import com.cluz.stocktracker.entity.User;
import com.cluz.stocktracker.mapper.UserMapper;
import com.cluz.stocktracker.service.AuthService;
import com.cluz.stocktracker.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/user")
@RequiredArgsConstructor
public class UserController {
	private final AuthService authService;

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> addUser(@RequestHeader(value = "isAdmin", required = false) boolean isAdmin,
										@RequestBody UserRegisterRequest request) {
		var user = UserMapper.toUser(request);
		authService.addUser(user, isAdmin);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.username(), request.password());
		Authentication authenticate = authenticationManager.authenticate(userAndPass);

		User user = (User) authenticate.getPrincipal();

		String token = tokenService.generateToken(user);

		return ResponseEntity.ok(AuthResponse.builder()
				.accessToken(token)
				.nome(user.getName())
				.build());
	}
}
