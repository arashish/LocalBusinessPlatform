package com.localbusinessplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.MessageCenter;
import com.localbusinessplatform.model.Orderx;

public interface MessageCenterRepository extends JpaRepository<MessageCenter, Long>{
	MessageCenter findByMessageId(long mesage_id);
	List<MessageCenter> findBySenderUsernameOrRecipientUsername(String sender_username, String recipient_username);
}
