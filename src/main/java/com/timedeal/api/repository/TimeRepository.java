package com.timedeal.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.api.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {

	Optional<Time> findByProductId(Long productId);

	Optional<Time> findByProductIdAndIsOverFalse(Long productId);

	List<Time> findByIsOverFalse();

}