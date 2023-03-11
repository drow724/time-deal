package com.timedeal.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timedeal.api.entity.Product;
import com.timedeal.api.http.request.ProductRequest;

public interface ProductUseCase {

	public Product get(Long id);

	public Page<Product> getList(Pageable pageable);
	
	public Product save(ProductRequest request);

	public Product update(Long id, ProductRequest request);

	public Product delete(Long id);

}