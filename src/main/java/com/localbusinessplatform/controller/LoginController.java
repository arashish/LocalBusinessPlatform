package com.localbusinessplatform.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.UserRepository;

@RestController
public class LoginController {

	@Autowired
	UserRepository repo;
	
	@CrossOrigin
	@GetMapping(value ={"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public User home() {
		User user = new User();
		user.setFirstname("Ace");
		user.setLastname("Rajak");
		return user;
	}
	
	@CrossOrigin
	@GetMapping(value ={"/login"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String login() {
		return "Welcome";
	}
	
	

}
