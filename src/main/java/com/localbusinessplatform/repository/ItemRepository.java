package com.localbusinessplatform.repository;

import com.localbusinessplatform.model.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{

	List<Item> findByStoreId(long store_id);
	
	Item findByItemId(long item_id);
	
}