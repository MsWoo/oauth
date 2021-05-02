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
import com.example.oauth.entity.Provider;
import com.example.oauth.entity.User;
import com.example.oauth.repository.UserRepository;
import com.example.oauth.auth.MyUserDetails;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
	private final UserRepository repo;
	
	//구글로그인인 경우에 DB Entity의 나머지 빈 값들을 채워준다.
	//초기비밀번호는 1234, 한번 구글로그인해서 DB에 들어오면 사이트내 1234입력해서 로그인 가능.
	public void processOAuthPostLogin(CustomOAuth2User oauthUser) {
		String email = oauthUser.getEmail();
		String username = oauthUser.getName();

		User existUser = repo.findUserByEmail(email);
		
		if (existUser == null) {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setUsername(username);
			newUser.setProvider(Provider.GOOGLE);
			newUser.setEnabled(true);		
			newUser.setRole("USER");
			newUser.setPassword("1234");
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			
			repo.saveUser(newUser);
			
			System.out.println("Created new user: " + username);
		}
	}

	
	//loadUserByUsername 메소드를 오버라이드해야해서 메소드명은 ByUsername이지만 
	//실제 로그인 인증에서는 email로 비교한다.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findUserByEmail(email);
		return new MyUserDetails(user);
	}
	
	@Transactional
	public void joinUser(User user){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.saveUser(user);
	}
	
	public List<User> findAll() {
		return repo.findAllUser();
	}
	
	public void delete(Long id) {
		repo.deleteUser(id);
	}
	
}
