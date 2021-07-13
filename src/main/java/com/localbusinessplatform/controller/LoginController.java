package com.localbusinessplatform.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbusinessplatform.constant.LBPConstants;
import com.localbusinessplatform.impl.UserPrincipal;
import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.StoreRepository;
import com.localbusinessplatform.repository.UserRepository;
import com.localbusinessplatform.util.JwtUtil;

@RestController
public class LoginController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StoreRepository storeRepository;
	
	
	@Autowired
	JwtUtil jwtUtil;
	
	@CrossOrigin
	@GetMapping(value = { "/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String home() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		System.out.println(jwtUtil.generateToken(user.getUsername()));
		return jwtUtil.generateToken(user.getUsername());
	}
	 

	@CrossOrigin
	@GetMapping(value = { "/home" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public User login() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		return user;
	}

	@CrossOrigin
	@PostMapping(value = { "/signup" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@RequestBody User user) throws Exception {
		user.setActive(false); // default
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		user.setRegistrationdate(dtf.format(localDate));
		userRepository.save(user);
		return LBPConstants.Status_OK;
	}

	@CrossOrigin
	@PostMapping(value = "/updateprofile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String profile(@RequestBody User user) throws Exception {
		Optional<User> finduser = userRepository.findById(user.getId());
		if (finduser.isPresent()) {
			User existingUser = finduser.get();
			userRepository.save(user);
			// existing user
		} else {
			// there is no user the repo with the given 'id'
		}
		return LBPConstants.Status_OK;
	}
	
	@CrossOrigin
	@GetMapping(value = { "/logoutsession" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
		return LBPConstants.Status_OK;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/createstore" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createStore(@RequestBody Store store) throws Exception {
		store.setPublish(false); // default
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		store.setRegistration_date(dtf.format(localDate));
		storeRepository.save(store);
		return LBPConstants.Status_OK;
	}
	
	
	
	
}
