package com.timedeal.api.service.impl;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Product get(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
	}
	
	@Override
	public Page<Product> getList(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	@Override
	public Product save(ProductDto dto) {
		return productRepository.save(new Product(dto));
	}

	@Override
	public Product update(Long id, ProductDto dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		product.update(dto);
		return product;
	}
}