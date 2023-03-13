package com.timedeal.api.service;

import java.util.NoSuchElementException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Product;
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
	public void persist(Member member, OrderRequest request) {
		Product product = repository.findById(request.getProductId())
				.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		product.order(request.getOrderCount());
	}
}