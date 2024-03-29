package com.localbusinessplatform.repository;

import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>{

	Store findByUserId(long user_id);
	
	Store findByStoreId(long store_id);
	
	Store findByStoreIdAndPublish(long store_id, boolean publish);
	
	@Transactional
	public void deleteByStoreId(long store_id);
}
