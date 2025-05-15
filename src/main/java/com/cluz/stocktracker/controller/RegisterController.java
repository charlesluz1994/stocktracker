package com.cluz.stocktracker.controller;

import com.cluz.stocktracker.controller.request.UserRegisterRequest;
import com.cluz.stocktracker.mapper.UserMapper;
import com.cluz.stocktracker.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/register")
@RequiredArgsConstructor
public class RegisterController {
	private final RegisterService registerService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Add user to application")
	public ResponseEntity<Void> addUser(@RequestHeader(value = "isAdmin", required = false) boolean isAdmin,
										@RequestBody UserRegisterRequest request) {
		var user = UserMapper.toUser(request);
		registerService.registerUser(user, isAdmin);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
