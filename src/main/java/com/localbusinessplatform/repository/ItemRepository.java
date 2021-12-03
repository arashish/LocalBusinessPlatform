package com.localbusinessplatform.repository;

import com.localbusinessplatform.model.Item;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long>{

	List<Item> findByStoreId(long store_id);
	
	Item findByItemId(long item_id);
	
	@Query(value = "SELECT * FROM item where item_name like CONCAT('%', :itemName,'%') or description like CONCAT('%', :description,'%') AND category= :category", nativeQuery = true)
	List<Item> findByItemNameOrDescriptionAndCategoryContains(@Param("itemName") String itemName,@Param("description") String description,@Param("category") String category);
	
	List<Item> findByItemNameOrDescriptionContains(String itemName, String desciption);
	
	List<Item> findByCategory(String category);
	
	@Transactional
	public void deleteByItemId(long item_id);
	
	@Transactional
	public void deleteByStoreId(long store_id);
	
}
