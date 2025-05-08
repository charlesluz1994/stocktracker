package com.cluz.stocktracker.config;

import com.cluz.stocktracker.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JWTUserData {
	private String userId;
	private String email;
	private List<UserRole> userRoles;
}
