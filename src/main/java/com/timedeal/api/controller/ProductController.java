package com.timedeal.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.entity.Product;
import com.timedeal.api.http.request.ProductRequest;
import com.timedeal.api.http.response.ProductResponse;
import com.timedeal.api.service.ProductUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductUseCase productUseCase;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
		Product product = productUseCase.get(id);
		return ResponseEntity.ok(new ProductResponse(product));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<Product> page = productUseCase.getList(pageable);
		return ResponseEntity.ok(new PageImpl<>(page.map(product -> new ProductResponse(product)).toList(), page.getPageable(), page.getTotalPages()));
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request) {
		Product product = productUseCase.save(request);
		return ResponseEntity.ok(new ProductResponse(product));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
		Product product = productUseCase.update(id, request);
		return ResponseEntity.ok(new ProductResponse(product));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
		Product product = productUseCase.delete(id);
		return ResponseEntity.ok(new ProductResponse(product));
	}
}
