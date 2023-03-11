package com.timedeal.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/{id}")
	public Product get(@PathVariable Long id) {
		return productService.get(id);
	}
	
	@GetMapping
	public Page<Product> getList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return productService.getList(pageable);
	}
	
	@PostMapping
	public Product save(@RequestBody ProductDto dto) {
		return productService.save(dto);
	}
	
	@PutMapping("/{id}")
	public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
		return productService.update(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public Product delete(@PathVariable Long id) {
		return null;
	}
}
