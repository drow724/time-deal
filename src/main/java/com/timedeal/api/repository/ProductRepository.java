package com.timedeal.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
