package com.localbusinessplatform.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.Store;

@Component
public class SearchData {
	
	Store store;
	
	Item item;
	
	String distance;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
}
