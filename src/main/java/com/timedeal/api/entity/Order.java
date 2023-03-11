package com.timedeal.api.entity;

import com.timedeal.api.entity.base.Audit;
import com.timedeal.api.entity.embedded.Address;
import com.timedeal.api.http.request.OrderRequest;
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

@Entity(name = "orders")
@Getter
public class Order extends Audit {

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
	
	private Integer orderCount;
	
	@Embedded
	private Address address;
	
	@Column(columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean deliveryYn = Boolean.FALSE;
	
	public Order(Member member, Product product, OrderRequest request) {
		if(product.getStock() < request.getOrderCount()) {
			throw new IllegalArgumentException("재고 수량이 부족합니다.");
		}
		this.member = member;
		this.orderCount = request.getOrderCount();
		this.product = product;
		product.order(this.orderCount);
		
		this.address = request.getAddress();
	}
	
	public void update(OrderRequest request) {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		deliveryYn = Boolean.TRUE;
	}

	
}
