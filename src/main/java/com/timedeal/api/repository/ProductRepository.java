package com.timedeal.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByIdAndDelYnFalse(Long id);
	
	Page<Product> findByDelYnFalse(Pageable pageable);

	List<Product> findByDelYnFalse();
}
