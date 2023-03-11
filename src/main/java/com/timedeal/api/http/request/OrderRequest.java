package com.timedeal.api.http.request;

import com.timedeal.api.entity.embedded.Address;

import lombok.Getter;

@Getter
public class OrderRequest {

	private Long productId;
	
	private Integer orderCount;
	
	private Address address;
	
}
