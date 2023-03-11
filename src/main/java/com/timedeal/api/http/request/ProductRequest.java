package com.timedeal.api.http.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Getter;

@Getter
public class ProductRequest {

	private Long id;
	
	private String name;
	
	private Integer stock;
	
	@Lob
	@Column(columnDefinition="BLOB")
	private byte[] image;
	
}