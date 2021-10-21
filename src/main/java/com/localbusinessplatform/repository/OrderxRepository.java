package com.localbusinessplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.Orderx;
import com.localbusinessplatform.model.User;


public interface OrderxRepository extends JpaRepository<Orderx, Long> {
	
	Orderx findByOrderId(long order_id);
	
	List<Orderx> findByStoreId(long store_id);
	
	List<Orderx> findByCustomerId(long store_id);
}
