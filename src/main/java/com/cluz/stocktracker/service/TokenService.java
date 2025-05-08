package com.cluz.stocktracker.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cluz.stocktracker.config.JWTUserData;
import com.cluz.stocktracker.entity.User;
import com.cluz.stocktracker.entity.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenService {

	@Value("${security.securityLoginKey}")
	private String secret;

	public String generateToken(User user) {
		Algorithm algorithm = Algorithm.HMAC256(secret);

		return JWT.create()
				.withIssuer("stocktracker-api")
				.withClaim("userId", user.getId())
				.withSubject(user.getEmail())
				.withClaim("roles", user.getUserRoles().stream().map(Enum::name).toList())
				.withExpiresAt(Instant.now().plusSeconds(84600))
				.withIssuedAt(Instant.now())
				.sign(algorithm);
	}


	public Optional<JWTUserData> validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);

			DecodedJWT tokenDecoded = JWT.require(algorithm)
					//.withIssuer("stocktracker-api")
					.build()
					.verify(token);

			return Optional.of(JWTUserData.builder()
					.email(tokenDecoded.getSubject())
					.userId(tokenDecoded.getClaim("userId").asString())
					.userRoles(tokenDecoded.getClaim("roles").asList(UserRole.class))
					.build());
		} catch (JWTVerificationException ex) {
			return Optional.empty();
		}
	}
}