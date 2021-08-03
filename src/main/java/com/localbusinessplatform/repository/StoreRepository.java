package com.localbusinessplatform.repository;

import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>{

	Store findByUserId(long user_id);
}
