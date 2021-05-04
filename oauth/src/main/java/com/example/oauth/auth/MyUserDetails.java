package com.example.oauth.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.oauth.entity.User;
import com.example.oauth.entity.UserRole;

public class MyUserDetails implements UserDetails {

	private User user;
	
	private String email;
	private String name;
	
	public MyUserDetails(User user) {
		this.user = user;
		this.email = user.getEmail();
		this.name = user.getUsername();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String roles = user.getRoles().toString();
		String[] authStrings = roles.substring(1, roles.length()-1).split(", ");
		for(String authString : authStrings) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+authString));
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

}
