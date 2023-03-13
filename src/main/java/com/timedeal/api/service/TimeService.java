package com.timedeal.api.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.Time;
import com.timedeal.api.entity.redis.TimeWrapper;
import com.timedeal.api.http.request.TimeRequest;
import com.timedeal.api.repository.TimeRepository;
import com.timedeal.api.repository.redis.ProductRedisRepository;
import com.timedeal.api.repository.redis.TimeRedisRepository;
import com.timedeal.common.util.LocalDateTimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeService implements TimeUseCase {

	private final TimeRepository timeRepository;

	private final TimeRedisRepository timeRedisRepository;

	private final ProductRedisRepository productRedisRepository;

	@Override
	public Time save(TimeRequest request) throws IllegalAccessException {
		LocalDateTime ttl = LocalDateTime.of(request.getDate(), request.getTime());
		System.out.println(ttl);
		System.out.println(LocalDateTimeUtil.now());
		System.out.println(LocalDateTimeUtil.now().isAfter(ttl));
		if (LocalDateTimeUtil.now().isBefore(ttl)) {
			throw new IllegalAccessException("타임딜 시간이 현재 시간보다 이전입니다.");
		}

		Product product = productRedisRepository.findById(request.getProductId().toString())
				.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다.")).getProduct();

		timeRepository.findByProductIdAndIsOverFalse(request.getProductId()).ifPresent(time -> {
			LocalDateTime validation = LocalDateTime.of(time.getDate(), time.getTime());
			if (validation.isBefore(LocalDateTimeUtil.now())) {
				time.over();
			} else {
				throw new IllegalArgumentException("해당 상품의 타임딜이 이미 존재합니다.");
			}
		});

		Time time = timeRepository.save(new Time(product, request));

		timeRedisRepository.save(new TimeWrapper(product, time));

		return time;
	}

	@Override
	public Time update(TimeRequest request) throws IllegalAccessException {
		LocalDateTime ttl = LocalDateTime.of(request.getDate(), request.getTime());

		if (LocalDateTimeUtil.now().isAfter(ttl)) {
			throw new IllegalAccessException("타임딜 시간이 현재 시간보다 이전입니다.");
		}

		Time time = timeRepository.findByProductId(request.getProductId())
				.orElseThrow(() -> new NoSuchElementException("해당 상품의 타임딜이 존재하지 않습니다."));

		time.update(request);

		TimeWrapper wrapper = timeRedisRepository.findById(request.getProductId().toString())
				.orElseThrow(() -> new NoSuchElementException("해당 상품의 타임딜이 존재하지 않습니다."));
		
		wrapper.update(time);
		timeRedisRepository.save(wrapper);

		return time;
	}
}