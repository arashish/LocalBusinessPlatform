package com.localbusinessplatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="message_id")
    private long messageId;
	
	@Column(name="sender_username")
	private String senderUsername;

	@Column(name="recipient_username")
	private String recipientUsername;
	
	private String message;
	
	@Column(name="message_date")
	private String messageDate;
	
	@Column(name="message_time")
	private String messageTime;
	
	@Column(name="message_status")
	private String messageStatus;

	@Column(name="message_category")
	private String messageCategory;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getRecipientUsername() {
		return recipientUsername;
	}

	public void setRecipientUsername(String recipientUsername) {
		this.recipientUsername = recipientUsername;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getMessageCategory() {
		return messageCategory;
	}

	public void setMessageCategory(String messageCategory) {
		this.messageCategory = messageCategory;
	}

	
}
