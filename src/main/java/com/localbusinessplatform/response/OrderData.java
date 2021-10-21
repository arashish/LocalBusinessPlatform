package com.localbusinessplatform.response;

import java.util.List;

import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.stereotype.Component;

import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.Orderx;
import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;

@Component
public class OrderData {
	
	Orderx order;
	
	User customer;
	
	Store store;
	
	Item item;
	
	
	public OrderData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderData(OrderData orderData) {
		this.order = orderData.getOrder();
		this.customer = orderData.getCustomer();
		this.store = orderData.getStore();
		this.item = orderData.getItem();
	}

	public Orderx getOrder() {
		return order;
	}

	public void setOrder(Orderx order) {
		this.order = order;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
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
	
	
	
	

	
}
