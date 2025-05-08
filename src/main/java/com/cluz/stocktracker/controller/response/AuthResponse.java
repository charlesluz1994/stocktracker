package com.cluz.stocktracker.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {

	@JsonProperty("acess_token")
	private String accessToken;

	private String nome;
}