package com.timedeal.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timedeal.api.dto.ProductMemberDto;
import com.timedeal.api.entity.Member;
import com.timedeal.api.entity.Order;
import com.timedeal.api.http.request.OrderRequest;

public interface OrderUseCase {

	public Page<ProductMemberDto> getOrderByMember(Long memberId, Pageable pageable);
	
	public Page<Member> getOrderByProduct(Long id, Pageable pageable);
	
	public Order save(Member member, OrderRequest request) throws IllegalAccessException;

}
