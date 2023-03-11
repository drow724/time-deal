package com.timedeal.api.entity;

import com.timedeal.api.dto.OrderDto;
import com.timedeal.api.entity.embedded.Address;
import com.timedeal.common.util.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Embedded
	private Address address;
	
	@Column(columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean deliveryYn = Boolean.FALSE;
	
	public Order(OrderDto dto) {
		// TODO Auto-generated constructor stub
	}
	
	public void update(OrderDto dto) {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		deliveryYn = Boolean.TRUE;
	}

	
}
