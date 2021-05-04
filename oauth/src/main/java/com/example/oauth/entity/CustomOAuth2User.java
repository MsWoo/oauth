package com.example.oauth.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.oauth.repository.UserRepository;
import com.example.oauth.service.UserService;

public class CustomOAuth2User implements OAuth2User {

	private OAuth2User oauth2User;
	
	public CustomOAuth2User(OAuth2User oauth2User) {
		this.oauth2User = oauth2User;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		
//		String curEmail = oauth2User.getAttribute(getEmail());
//		User curUser = this.service.findUserByEmail(curEmail);
//		String roles = curUser.getRoles().toString();
//		String[] authStrings = roles.substring(1, roles.length()-1).split(", ");
//		for(String authString : authStrings) {
////			System.out.println(authString);
//			authorities.add(new SimpleGrantedAuthority("ROLE_"+authString));
//		}
//		
//		return authorities;
		
//		System.out.println(oauth2User.getAuthorities());

		
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {	
		return oauth2User.getAttribute("name");
	}

	public String getEmail() {
		return oauth2User.<String>getAttribute("email");		
	}

}
