package com.timedeal.api.entity.redis;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import com.timedeal.api.entity.Product;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash("product")
@NoArgsConstructor
public class ProductWrapper implements Serializable {

	private static final long serialVersionUID = 7134607805727064981L;

	@Id
	private String id;
	
	private Product product;
	
	public ProductWrapper(Product product) {
		this.id = product.getId().toString();
		this.product = product;
	}

	public void update(Product product) {
		this.product = product;
	}

}
