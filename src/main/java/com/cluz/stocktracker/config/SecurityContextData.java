package com.cluz.stocktracker.config;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityContextData {
	/**
	 * Gets the principal value of the token, that is "userId".
	 */
	public static JWTUserData getUserData(){
		return (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
