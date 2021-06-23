package com.localbusinessplatform.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localbusinessplatform.constant.LBPConstants;
import com.localbusinessplatform.impl.UserPrincipal;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.UserRepository;

@RestController
public class LoginController {

	@Autowired
	UserRepository repo;
	
	@CrossOrigin
	@GetMapping(value ={"/"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public User home() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		return user;
	}
	
	@CrossOrigin
	@GetMapping(value ={"/login"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String login() {
		return "Welcome";
	}
	
	@CrossOrigin
	@PostMapping(value ={"/signup"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@RequestBody User user) throws Exception {
		user.setActive(false); //default
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		user.setRegistrationdate(dtf.format(localDate));
		repo.save(user);
		return LBPConstants.Status_OK;
	}
	
	@CrossOrigin
	@PostMapping(value ="/updateprofile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String profile(@RequestBody User user) throws Exception {
		Optional<User> finduser = repo.findById(user.getId());
		if(finduser.isPresent()) {
		    User existingUser = finduser.get();
		    repo.save(user);
		    //existing user
		} else {
		    //there is no user the repo with the given 'id'
		}
		return LBPConstants.Status_OK;
	}

}
