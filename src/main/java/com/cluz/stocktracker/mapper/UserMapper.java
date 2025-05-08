package com.cluz.stocktracker.mapper;

import com.cluz.stocktracker.controller.request.UserRegisterRequest;
import com.cluz.stocktracker.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
	public static User toUser(UserRegisterRequest request) {
		return User.builder()
				.name(request.name())
				.email(request.email())
				.password(request.password())
				.build();
	}
}
