package com.timedeal.api.service;

import com.timedeal.api.dto.OrderDto;
import com.timedeal.api.entity.Order;

public interface OrderService {

	public Order save(OrderDto dto);

}
