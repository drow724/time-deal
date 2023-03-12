package com.timedeal.api.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.Time;
import com.timedeal.api.http.request.TimeRequest;
import com.timedeal.api.repository.ProductRepository;
import com.timedeal.api.repository.TimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeService implements TimeUseCase {

	private final TimeRepository timeRepository;

	private final ProductRepository productRepository;

	private final RedisTemplate<String, LocalDateTime> template;

	@Override
	public Time save(TimeRequest request) {
		Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		
		Time time = timeRepository.save(new Time(product, request));
		
		LocalDateTime ttl = LocalDateTime.of(request.getDate(), request.getTime());
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime ldt = LocalDateTime.of(now.getYear(),
		now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
		
		ValueOperations<String, LocalDateTime> valueOperations = template.opsForValue();

		valueOperations.set(request.getProductId().toString(), ttl);
		template.expire(request.getProductId().toString(), Duration.between(now, ldt).getSeconds(), TimeUnit.SECONDS);
		return time;	
	}
}