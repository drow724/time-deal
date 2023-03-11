package com.timedeal.api.http.request;

import lombok.Getter;

@Getter
public class OrderRequest {

	private Long productId;
	
	private Integer orderCount;
	
	private String city;
	
}
