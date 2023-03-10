package com.timedeal.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timedeal.api.dto.ProductDto;
import com.timedeal.api.entity.Product;

public interface ProductService {

	public Product get(Long id);

	public Page<Product> getList(Pageable pageable);
	
	public Product save(ProductDto dto);

	public Product update(Long id, ProductDto dto);

	public Product delete(Long id);

}