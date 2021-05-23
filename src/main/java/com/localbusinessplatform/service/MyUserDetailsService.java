package com.localbusinessplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.UserRepository;

public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.findByUsername(username);
		if (user==null)
			throw new UsernameNotFoundException("User not found - 404");
		
		return new UserPrincipal(user);
	}

}
