package com.localbusinessplatform.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.localbusinessplatform.impl.UserPrincipal;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.UserRepository;
import com.localbusinessplatform.service.MyUserDetailsService;

@Controller
public class LoginController {

	@Autowired
	UserRepository repo;

	MyUserDetailsService userdetails;

	@GetMapping({ "/", "/login" })
	public String home(Principal principal) {
		 if (principal!=null && ((Authentication)principal).isAuthenticated()) 
		 	{
		        return "forward:/home"; //redirects to home page if already logged in
		    }
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}

	@GetMapping("/home")
	public String homePage(Model model) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("name", principal.getUser().getFirstname() + " " + principal.getUser().getLastname());
		return "home";
	}

	@PostMapping("/adduser")
	public String addUser(User user) {
		repo.save(user);
		return "signup";
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = repo.findById(principal.getUser().getId());
		model.addAttribute("user", principal.getUser());
		return "profile";
	}
	

	@PostMapping("/updateuser")
	public String updateUser(User user) {
		repo.save(user);
		return "home";
	}
	
	
	@GetMapping("/logout")
	public String logoutPage() {
		return "login";
	}

}
