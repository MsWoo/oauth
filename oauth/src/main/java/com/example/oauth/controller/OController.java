package com.example.oauth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.oauth.auth.MyUserDetails;
import com.example.oauth.entity.CustomOAuth2User;
import com.example.oauth.entity.User;
import com.example.oauth.entity.UserRole;
import com.example.oauth.repository.UserRepository;
import com.example.oauth.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OController {
	
	private final UserService service;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	  public String home() {
	    return "home";
	  }
	
	@RequestMapping("/login")
	  public String loginForm() {
	    return "login";
	  }

    @GetMapping("/signup")
    public String signUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(User user) {
    	user.addUserRole(UserRole.USER);
//		user.addUserRole(UserRole.ADMIN);
        user.setEnabled(true);
        user.setFromSocial(false);
        service.joinUser(user);
        return "redirect:/login";
    }
    
    @RequestMapping("/mypage")
	private String mypage(Model model, Authentication authentication) {
    	
    	//MyUserDetail
    	if(authentication.getPrincipal() instanceof MyUserDetails) {
    		MyUserDetails userDetail = (MyUserDetails) authentication.getPrincipal();
            model.addAttribute("email", userDetail.getEmail()); 
            model.addAttribute("name", userDetail.getUsername());
    	}
    	//CustomOAuth2User
    	else {
    		CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        	model.addAttribute("email", oauthUser.getEmail());
        	model.addAttribute("name", oauthUser.getName());
    	}
    	
		return "mypage";
	}

    @RequestMapping("/list")
	private String userList(Model model, HttpServletRequest request) {
		List<User> userList = new ArrayList<>();
		userList = service.findAll();
		model.addAttribute("userlist", userList);
		return "list";
	}
    
    @RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id) {
		service.delete(id);
		return "redirect:/list";
	}
    
    @RequestMapping("/update/{id}")
	public String updateUser(@PathVariable(name = "id") Long id) {
    	User user = userRepository.findUserById(id);
    	service.giveAdmin(id);
		return "redirect:/list";	
	}
}
