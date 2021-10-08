package com.localbusinessplatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orderx {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
    private String orderId;
	
	@Column(name="customer_id")
    private String customerId;
	
	@Column(name="store_id")
    private String storeId;
	
	@Column(name="item_id")
    private String itemId;
	
	@Column(name="item_price")
    private String itemPrice;
	
	@Column(name="order_qty")
    private String orderQty;
	
	@Column(name="order_status")
    private String orderStatus;
	
	@Column(name="order_date")
    private String orderDate;
	
	@Column(name="payment_method")
    private String paymentMethod;
	
	@Column(name="ship_method")
    private String shipMethod;
	
	@Column(name="shipped_date")
    private String shippedDate;
	
	@Column(name="ship_via")
    private String shipVia;
    
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(String shippedDate) {
		this.shippedDate = shippedDate;
	}
	public String getShipVia() {
		return shipVia;
	}
	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getShipMethod() {
		return shipMethod;
	}
	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}
    
	
}
