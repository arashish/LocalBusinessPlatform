package com.localbusinessplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.MessageCenter;
import com.localbusinessplatform.model.Orderx;

public interface MessageCenterRepository extends JpaRepository<MessageCenter, Long>{
	MessageCenter findByMessageId(long mesage_id);
}
