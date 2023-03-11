package com.timedeal.api.http.response;

import com.timedeal.api.entity.Order;
import com.timedeal.api.entity.embedded.Address;

import lombok.Getter;

@Getter
public class OrderResponse {
	
	private final Long id;
	
	private final Address address;
	
	private final Integer orderCount;
	
	private final Boolean deliveryYn;
	
	public OrderResponse(Order order) {
		this.id = order.getId();
		this.address = order.getAddress();
		this.orderCount = order.getOrderCount();
		this.deliveryYn = order.getDeliveryYn();
	}

}
