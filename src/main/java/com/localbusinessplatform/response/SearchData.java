package com.localbusinessplatform.response;

import java.util.List;

import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.stereotype.Component;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.Store;

@Component
public class SearchData {
	
	Store store;
	
	Item item;
	
	String distance;

	
	
	public SearchData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchData(SearchData searchData) {
		this.store = searchData.getStore();
		this.item = searchData.getItem();
		this.distance = searchData.getDistance();
	}

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
