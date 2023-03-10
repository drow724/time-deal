package com.timedeal.api.entity;

import com.timedeal.api.dto.ProductDto;
import com.timedeal.common.util.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Column(columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean delYn = Boolean.FALSE;
	
	private Integer stock;
	
	public Product(ProductDto dto) {
		this.name = dto.getName();
		this.stock = dto.getStock();
	}
}
