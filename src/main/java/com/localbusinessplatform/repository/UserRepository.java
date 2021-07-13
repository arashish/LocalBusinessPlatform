package com.localbusinessplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localbusinessplatform.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String email);

}
