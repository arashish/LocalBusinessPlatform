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
    private long orderId;
	
	@Column(name="customer_id")
    private long customerId;
	
	@Column(name="store_id")
    private long storeId;
	
	@Column(name="item_id")
    private long itemId;
	
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
	
	@Column(name="ship_tracking")
    private String shipTracking;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
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

	public String getShipTracking() {
		return shipTracking;
	}

	public void setShipTracking(String shipTracking) {
		this.shipTracking = shipTracking;
	}
    
}
