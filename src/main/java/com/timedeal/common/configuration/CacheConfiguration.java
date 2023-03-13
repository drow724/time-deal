package com.timedeal.common.configuration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.Time;
import com.timedeal.api.repository.ProductRepository;
import com.timedeal.api.repository.TimeRepository;
import com.timedeal.common.util.LocalDateTimeUtil;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CacheConfiguration {

	private final TimeRepository timeRepository;

	private final ProductRepository productRepository;
	
	private final RedisTemplate<String, LocalDateTime> timeTemplate;

	private final RedisTemplate<String, Product> productTemplate;
	
	@PostConstruct
	@Transactional
	public void timeInit() {
		List<Time> list = timeRepository.findByIsOverFalse();
		
		ValueOperations<String, LocalDateTime> valueOperations = timeTemplate.opsForValue();

		list.parallelStream()
				.filter(time -> LocalDateTimeUtil.now().isAfter(LocalDateTime.of(time.getDate(), time.getTime())))
				.forEach(time -> time.over());

		list.parallelStream().forEach(time -> {
			LocalDateTime ttl = LocalDateTime.of(time.getDate(), time.getTime());
			valueOperations.set(time.getProduct().getId().toString(), ttl,
					Duration.between(LocalDateTimeUtil.now(), ttl).getSeconds(), TimeUnit.SECONDS);
		});
	}
	
	@PostConstruct
	@Transactional
	public void productInit() {
		List<Product> list = productRepository.findByDelYnFalse();
		
		ValueOperations<String, Product> valueOperations = productTemplate.opsForValue();

		list.parallelStream().forEach(product -> {
			valueOperations.set(product.getId().toString(), product);
		});
	}
}
