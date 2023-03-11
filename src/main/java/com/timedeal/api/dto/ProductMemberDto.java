package com.timedeal.api.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductMemberDto {

	private Long id;
	
	private String name;
	
	private Integer orderCount;
	
	@QueryProjection
	public ProductMemberDto(Long id, String name, Integer orderCount) {
		this.id = id;
		this.name = name;
		this.orderCount = orderCount;
	}
}