package com.timedeal.api.service;

import com.timedeal.api.dto.ProductDto;
import com.timedeal.api.entity.Product;

public interface ProductService {

	public Product save(ProductDto dto);

}
