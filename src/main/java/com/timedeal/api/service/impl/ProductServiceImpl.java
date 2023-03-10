package com.timedeal.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.dto.ProductDto;
import com.timedeal.api.entity.Product;
import com.timedeal.api.repository.ProductRepository;
import com.timedeal.api.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	
	@Override
	public Product save(ProductDto dto) {
		return productRepository.save(new Product(dto));
	}

}