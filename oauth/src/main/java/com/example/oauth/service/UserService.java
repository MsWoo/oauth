package com.example.oauth.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.oauth.auth.MyUserDetails;
import com.example.oauth.entity.CustomOAuth2User;
import com.example.oauth.entity.User;
import com.example.oauth.entity.UserRole;
import com.example.oauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository repo;

	// 구글로그인인 경우에 DB Entity의 나머지 빈 값들을 채워준다.
	// 초기비밀번호는 1234, 한번 구글로그인해서 DB에 들어오면 사이트내 1234입력해서 로그인 가능.
	public void processOAuthPostLogin(CustomOAuth2User oauthUser) {
		String email = oauthUser.getEmail();
		String name = oauthUser.getName();

		User existUser = repo.findUserByEmail(email);

		if (existUser == null) {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setUsername(name);
			newUser.setFromSocial(true);
			newUser.setEnabled(true);
			newUser.addUserRole(UserRole.USER);
			newUser.setPassword("1234");
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

			repo.saveUser(newUser);
		}
	}

	@Transactional
	public void joinUser(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.saveUser(user);
	}

	public List<User> findAll() {
		return repo.findAllUser();
	}
	
	public User findUserByEmail(String Email) {
		return repo.findUserByEmail(Email);
	}

	public void delete(Long id) {
		repo.deleteUserRole(id);
		repo.deleteUser(id);
	}
	
	public void giveAdmin(Long id) {
		repo.giveAdmin(id);
	}

}
