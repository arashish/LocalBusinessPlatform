package com.localbusinessplatform.repository;

import com.localbusinessplatform.model.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{

	Item findByItemId(long item_id);
}
