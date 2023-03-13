package com.timedeal.api.service;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.redis.ProductWrapper;
import com.timedeal.api.http.request.ProductRequest;
import com.timedeal.api.repository.ProductRepository;
import com.timedeal.api.repository.redis.ProductRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

	private final ProductRepository repository;

	private final ProductRedisRepository redis;

	@Override
	@Transactional(readOnly = true)
	public Product get(Long id) {
		return redis.findById(id.toString()).orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."))
				.getProduct();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Product> getList(Pageable pageable) {
		Page<ProductWrapper> page = redis.findAll(pageable);
		return new PageImpl<>(page.map(data -> data.getProduct()).toList(), pageable, page.getTotalElements());
	}

	@Override
	public Product save(ProductRequest request) {
		Product product = repository.save(new Product(request));
		redis.save(new ProductWrapper(product));
		return product;
	}

	@Override
	public Product update(Long id, ProductRequest request) {
		Product product = repository.findByIdAndDelYnFalse(id)
				.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		product.update(request);
		
		ProductWrapper wrapper = redis.findById(id.toString()).orElseThrow(() -> new NoSuchElementException());
		wrapper.update(product);
		
		redis.save(wrapper);
		
		return product;
	}

	@Override
	public Product delete(Long id) {
		Product product = repository.findByIdAndDelYnFalse(id)
				.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		product.delete();
	
		redis.deleteById(id.toString());
		return product;
	}
}