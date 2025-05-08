package com.cluz.stocktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User implements UserDetails {

	@Id
	private String id;

	private String name;

	@Indexed(unique = true)
	private String email;

	private String password;

	private List<UserRole> userRoles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userRoles.stream().map(userRole -> new SimpleGrantedAuthority("ROLE_".concat(userRole.name()))).toList();
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword(){
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
