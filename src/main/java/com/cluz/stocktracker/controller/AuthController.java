package com.cluz.stocktracker.controller;

import com.cluz.stocktracker.controller.request.AuthRequest;
import com.cluz.stocktracker.controller.response.AuthResponse;
import com.cluz.stocktracker.entity.User;
import com.cluz.stocktracker.service.AuthService;
import com.cluz.stocktracker.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/auth")
@RequiredArgsConstructor
public class AuthController {
	//TODO: Pass rules of login to this service.
	private final AuthService authService;

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

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
