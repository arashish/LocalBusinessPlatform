package com.localbusinessplatform.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;

@Component
public class UserData {
	
	User user;
	Store store;

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
	
}
