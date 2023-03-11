package com.timedeal.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.dto.LoginDto;
import com.timedeal.api.entity.Order;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.http.response.OrderResponse;
import com.timedeal.api.service.OrderUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderUseCase orderUseCase;
	
	private final HttpSession session;
	
	@PostMapping
	public ResponseEntity<OrderResponse> save(@RequestBody OrderRequest request) throws IllegalAccessException {
		LoginDto login = new LoginDto(session.getAttribute("login"));
		Order order = orderUseCase.save(login.getId(), request);
		return ResponseEntity.ok(new OrderResponse(order));
	}

}
