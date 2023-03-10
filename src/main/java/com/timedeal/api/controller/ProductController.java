package com.timedeal.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.dto.ProductDto;
import com.timedeal.api.entity.Product;
import com.timedeal.api.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@PostMapping
	public Product save(@RequestBody ProductDto dto) {
		return productService.save(dto);
	}
}
