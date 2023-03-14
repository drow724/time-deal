package com.timedeal.api.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.redis.ProductWrapper;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.repository.ProductRepository;
import com.timedeal.api.repository.redis.ProductRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AsyncProductService {

	private final ProductRepository repository;

	private final ProductRedisRepository redis;
	
	@Async
	public void cache(Member member, Product product) {
		redis.save(new ProductWrapper(product));
	}
	
	@Async
	public void persist(Member member, ProductWrapper productWrapper, OrderRequest request) {
		redis.save(productWrapper);
		Product product = repository.findById(productWrapper.getProduct().getId()).get();
		product.order(request.getOrderCount());
	}

}