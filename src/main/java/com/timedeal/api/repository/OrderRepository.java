package com.timedeal.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
