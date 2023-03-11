package com.timedeal.api.service;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Product;
import com.timedeal.api.http.request.ProductRequest;
import com.timedeal.api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

	private final ProductRepository productRepository;

	@Override
	@Transactional(readOnly = true)
	public Product get(Long id) {
		return productRepository.findByIdAndDelYnFalse(id).orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Product> getList(Pageable pageable) {
		return productRepository.findByDelYnFalse(pageable);
	}
	
	@Override
	public Product save(ProductRequest request) {
		return productRepository.save(new Product(request));
	}

	@Override
	public Product update(Long id, ProductRequest request) {
		Product product = productRepository.findByIdAndDelYnFalse(id)
				.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		product.update(request);
		return product;
	}

	@Override
	public Product delete(Long id) {
		Product product = productRepository.findByIdAndDelYnFalse(id).orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		product.delete();
		return product;
	}
}