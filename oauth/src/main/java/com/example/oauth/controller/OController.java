package com.example.oauth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.oauth.auth.MyUserDetails;
import com.example.oauth.entity.User;
import com.example.oauth.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OController {
	
	private final UserService service;

	@RequestMapping("/")
	  public String home() {
	    return "home";
	  }
	
	@RequestMapping("/login")
	  public String loginForm() {
	    return "login";
	  }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    @PostMapping("/signUp")
    public String signUp(User user) {
        user.setRole("USER");
        user.setEnabled(true);
        service.joinUser(user);
        return "redirect:/login";
    }
    
    @RequestMapping("/MyPage")
	private String mypage(Model model, Authentication authentication) {
    	MyUserDetails userDetail = (MyUserDetails)authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("email", userDetail.getUsername());      //유저 이메일
        model.addAttribute("pw", userDetail.getPassword());
        model.addAttribute("name", userDetail.getName());
        model.addAttribute("role", userDetail.getRole());
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
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		service.delete(id);
		return "redirect:/list";
	}
    
}
