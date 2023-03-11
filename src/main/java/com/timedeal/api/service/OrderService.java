package com.timedeal.api.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Order;
import com.timedeal.api.entity.Product;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.repository.MemberRepository;
import com.timedeal.api.repository.OrderRepository;
import com.timedeal.api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;
	
	private final MemberRepository memberRepository;
	
	@Override
	public Order save(Long memberid, OrderRequest request) {
		Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NoSuchElementException("주문 상품이 존재하지 않습니다."));
		Member member = memberRepository.findById(memberid).orElseThrow(() -> new NoSuchElementException("로그인이 필요한 서비스입니다."));
		return orderRepository.save(new Order(member, product, request));
	}

}