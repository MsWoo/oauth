package com.example.oauth.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.oauth.entity.Provider;
import com.example.oauth.entity.Role;
import com.example.oauth.entity.User;

public class MyUserDetails implements UserDetails {

	private User user;
	
	private String email;
	private String name;
	private String role;
	private Provider provider;
	
	public MyUserDetails(User user) {
		this.user = user;
		this.email = user.getEmail();
		this.name = user.getUsername();
		this.role = user.getRole();
		this.provider = user.getProvider();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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
		return user.isEnabled();
	}
	
	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
	}
	public Provider getProvider() {
		return provider;
	}
}
