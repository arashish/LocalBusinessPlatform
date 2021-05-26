package com.localbusinessplatform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.localbusinessplatform.impl.UserPrincipal;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.UserRepository;

@Controller
public class LoginController {
	
	@Autowired
	UserRepository repo;
	
	@GetMapping("/")
	public String home() {
		return "login";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
	@GetMapping("/home")
	public String homePage() {
		return "home";
	}
	
	@PostMapping("/adduser")
	public String addUser(User user) {
		repo.save(user);
		return "signup";
	}
	
	
	@GetMapping("/logout")
	public String logoutPage() {
		return "login";
	}

}
