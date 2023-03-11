package com.timedeal.api.repository.query;

import static com.timedeal.api.entity.QMember.member;
import static com.timedeal.api.entity.QOrder.order;
import static com.timedeal.api.entity.QProduct.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import com.timedeal.api.dto.ProductMemberDto;
import com.timedeal.api.entity.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

	private final JPQLQueryFactory queryFactory;

	public Page<ProductMemberDto> findByMemberId(Long memberId, Pageable pageable) {
		QueryResults<ProductMemberDto> result = queryFactory
				.select(Projections.fields(ProductMemberDto.class, product.id, product.name, order.orderCount))
				.from(product)
				.join(product.orders, order).where(order.member.id.eq(memberId))
				.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}

	public Page<Member> findByProductId(Long productId, Pageable pageable) {
		QueryResults<Member> result = queryFactory.selectFrom(member).join(member.orders, order).fetchJoin()
				.where(order.product.id.eq(productId)).offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}

}