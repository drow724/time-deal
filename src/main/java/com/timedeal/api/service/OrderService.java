package com.timedeal.api.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.dto.ProductMemberDto;
import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Order;
import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.redis.ProductWrapper;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.repository.OrderRepository;
import com.timedeal.api.repository.ProductRepository;
import com.timedeal.api.repository.query.OrderQueryRepository;
import com.timedeal.api.repository.redis.ProductRedisRepository;
import com.timedeal.common.annotation.Time;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

	private final OrderRepository orderRepository;

	private final OrderQueryRepository orderQueryRepository;

	private final ProductRepository productRepository;
	
	private final ProductRedisRepository productRedisRepository;
	
	private final AsyncProductService asyncService;
	
	@Override
	public Page<ProductMemberDto> getOrderByMember(Long id, Pageable pageable) {
		return orderQueryRepository.findByMemberId(id, pageable);
	}

	@Override
	public Page<Member> getOrderByProduct(Long id, Pageable pageable) {
		return orderQueryRepository.findByProductId(id, pageable);
	}

	@Time
	@Override
	public Order save(Member member, OrderRequest request) throws IllegalAccessException {
		
		Optional<ProductWrapper> wrapper = productRedisRepository.findById(request.getProductId().toString());
		
		Product product = new Product();
		
		if(wrapper.isEmpty() || wrapper.get().getProduct() == null) {
			product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NoSuchElementException());
			product.order(request.getOrderCount());
			asyncService.cache(member, product);
		} else {
			product = wrapper.get().getProduct();
			product.order(request.getOrderCount());
			asyncService.persist(member, wrapper.get(), request);
		}
		
		return orderRepository.save(new Order(member, product, request));
	}

}