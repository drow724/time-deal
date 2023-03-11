package com.timedeal.api.service;

import com.timedeal.api.entity.Order;
import com.timedeal.api.http.request.OrderRequest;

public interface OrderUseCase {

	public Order save(Long id, OrderRequest request);

}
