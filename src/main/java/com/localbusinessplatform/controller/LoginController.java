package com.localbusinessplatform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.UserRepository;

@Controller
public class LoginController {
	
	@Autowired
	UserRepository repo;
	
	User user;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/login")
	public String loginPage(@RequestParam String username) {
		User user = repo.findByUsername(username);
		return repo.findById(user.getId());
	}
	
	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
	@PostMapping("/adduser")
	public String addUser(User user) {
		repo.save(user);
		return "signup";
	}
	
	
	@GetMapping("/logout")
	public String logoutPage() {
		return "home";
	}

}
