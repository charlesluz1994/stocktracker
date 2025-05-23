package com.cluz.stocktracker.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
		name = "beaterAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer"
)
public class OpenApiConfig {

	@Bean
	public OpenAPI stockOpenApi() {
		var contact = new Contact();
		contact.setEmail("charlesluz.94@gmail.com");
		contact.setName("Charles Luz");
		contact.setUrl("https://github.com/charlesluz1994/stocktracker");

		var info = new Info();
		info.title("Stock Tracker - Spring Boot REST API");
		info.version("v1");
		info.contact(contact);
		info.description("API for managing stock purchases on the stock exchange");

		var localServer = new Server();
		localServer.setUrl("http://localhost:8080");
		localServer.setDescription("Server URL local environment");

		var devServer = new Server();
		devServer.setUrl("http://dev.com");
		devServer.setDescription("Server URL DEV environment");

		return new OpenAPI().info(info).servers(List.of(localServer, devServer));
	}
}