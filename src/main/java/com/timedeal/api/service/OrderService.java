package com.timedeal.api.service;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.dto.ProductMemberDto;
import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Order;
import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.redis.ProductWrapper;
import com.timedeal.api.entity.redis.TimeWrapper;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.repository.OrderRepository;
import com.timedeal.api.repository.query.OrderQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

	private final OrderRepository orderRepository;

	private final OrderQueryRepository orderQueryRepository;

	private final RedisTemplate<String, TimeWrapper> timeTemplate;

	private final RedisTemplate<String, ProductWrapper> productTemplate;
	
	private final RedissonClient redissonClient;
	
	@Override
	public Page<ProductMemberDto> getOrderByMember(Long id, Pageable pageable) {
		return orderQueryRepository.findByMemberId(id, pageable);
	}

	@Override
	public Page<Member> getOrderByProduct(Long id, Pageable pageable) {
		return orderQueryRepository.findByProductId(id, pageable);
	}

	@Override
	public Order save(Member member, OrderRequest request) throws IllegalAccessException {
		
		HashOperations<String, String, TimeWrapper> timeOps = timeTemplate.opsForHash();

		TimeWrapper time = timeOps.get("time", request.getProductId().toString());

		if(time == null) {
			throw new NoSuchElementException("해당 상품의 타임딜이 존재하지 않습니다.");
		}
		
		HashOperations<String, String, ProductWrapper> productOps = productTemplate.opsForHash();
		ProductWrapper wrapper = productOps.get(request.getProductId().toString(), "product");
		
		Product product = wrapper.getProduct();
		product.order(request.getOrderCount());
		
		wrapper.update(product);
		productOps.put("product", request.getProductId().toString(), wrapper);
		
		return orderRepository.save(new Order(member, product, request));
	}

}