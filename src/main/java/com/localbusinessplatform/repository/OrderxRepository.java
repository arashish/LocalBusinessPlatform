package com.localbusinessplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localbusinessplatform.model.Orderx;
import com.localbusinessplatform.model.User;


public interface OrderxRepository extends JpaRepository<Orderx, Long> {
	
	User findByOrderId(long order_id);

}
