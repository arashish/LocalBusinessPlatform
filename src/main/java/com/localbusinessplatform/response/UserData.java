package com.localbusinessplatform.response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.MessageCenter;
import com.localbusinessplatform.model.Orderx;
import com.localbusinessplatform.model.Review;
import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;

@Component
public class UserData {
	
	User user;
	Store store;
	List<Item> item;
	List<MessageCenter> messageCenter;
	List<Orderx> order;
	List<Review> review;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public List<MessageCenter> getMessageCenter() {
		return messageCenter;
	}

	public void setMessageCenter(List<MessageCenter> messageCenter) {
		this.messageCenter = messageCenter;
	}
	
	public List<Orderx> getOrder() {
		return order;
	}

	public void setOrder(List<Orderx> order) {
		this.order = order;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}
	
}
