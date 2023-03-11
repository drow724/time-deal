package com.timedeal.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.dto.OrderDto;
import com.timedeal.api.entity.Order;
import com.timedeal.api.repository.OrderRepository;
import com.timedeal.api.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public Order save(OrderDto dto) {
		return orderRepository.save(new Order(dto));
	}

}