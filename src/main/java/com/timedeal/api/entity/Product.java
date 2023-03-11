package com.timedeal.api.entity;

import java.util.ArrayList;
import java.util.List;

import com.timedeal.api.dto.ProductDto;
import com.timedeal.common.util.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean delYn = Boolean.FALSE;
	
	private Integer stock;
	
	@OneToMany(mappedBy = "product")
	List<Order> orders = new ArrayList<>();
	
	public Product(ProductDto dto) {
		this.name = dto.getName();
		this.stock = dto.getStock();
	}

	public void update(ProductDto dto) {
		this.name = dto.getName();
		this.stock = dto.getStock();	
	}

	public void delete() {
		this.delYn = Boolean.TRUE;
	}
}
