package com.timedeal.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
