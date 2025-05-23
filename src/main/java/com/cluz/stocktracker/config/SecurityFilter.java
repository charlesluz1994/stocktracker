package com.cluz.stocktracker.config;

import com.cluz.stocktracker.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class SecurityFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		if (Strings.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			try {
				var token = authorizationHeader.substring("Bearer ".length());
				Optional<JWTUserData> optUser = tokenService.validateToken(token);

				if (optUser.isPresent()) {
					var userData = optUser.get();
					var authenticationToken = new UsernamePasswordAuthenticationToken(userData, null,
							userData.getUserRoles().stream()
									.map(userRole -> new SimpleGrantedAuthority("ROLE_".concat(userRole.name()))).toList());
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
				filterChain.doFilter(request, response);
			} catch (Exception ex) {
				response.setHeader("error", ex.getMessage());
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
