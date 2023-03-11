package com.timedeal.api.http.response;

import com.timedeal.api.entity.Product;

import lombok.Getter;

@Getter
public class ProductResponse {
	
	private final Long id;
	
	private final String name;
	
	private final Boolean delYn;
	
	private final Integer stock;
	
	public ProductResponse(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.delYn = product.getDelYn();
		this.stock = product.getStock();
	}

}
