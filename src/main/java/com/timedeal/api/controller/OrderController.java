package com.timedeal.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.dto.ProductMemberDto;
import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Order;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.http.response.MemberResponse;
import com.timedeal.api.http.response.OrderResponse;
import com.timedeal.api.service.AsyncProductService;
import com.timedeal.api.service.OrderUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderUseCase orderUseCase;

	private final HttpSession session;
	
	@GetMapping("/member/{id}")
	public ResponseEntity<Page<ProductMemberDto>> getMemberOrder(@PathVariable Long id,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<ProductMemberDto> products = orderUseCase.getOrderByMember(id, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Page<MemberResponse>> getProductOrder(@PathVariable Long id,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<Member> members = orderUseCase.getOrderByProduct(id, pageable);
		return ResponseEntity.ok(new PageImpl<>(members.map(member -> new MemberResponse(member)).toList(),
				members.getPageable(), members.getTotalPages()));
	}
	
	@PostMapping
	public ResponseEntity<OrderResponse> save(@RequestBody OrderRequest request) throws IllegalAccessException {
		Member member = (Member) session.getAttribute("login");
		Order order = orderUseCase.save(member, request);
		return ResponseEntity.ok(new OrderResponse(order));
	}

}
