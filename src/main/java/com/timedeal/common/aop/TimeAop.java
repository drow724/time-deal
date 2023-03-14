package com.timedeal.common.aop;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.timedeal.api.entity.Time;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.repository.redis.TimeRedisRepository;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class TimeAop {

	private final TimeRedisRepository timeRedisRepository;

	@Before("@annotation(com.timedeal.common.annotation.Time)")
	public void isBefore(JoinPoint joinPoint) throws IllegalAccessException {
		Object[] args = joinPoint.getArgs();
		
		OrderRequest request = new OrderRequest();
		
		for(Object arg : args) {
			if(arg instanceof OrderRequest) {
				request = (OrderRequest) arg;
			}
		}
		
		if(request.getProductId() == null) {
			throw new NoSuchElementException();
		}
		
		Time time = timeRedisRepository.findById(request.getProductId().toString()).orElseThrow(() -> new NoSuchElementException("해당 상품의 타임딜이 존재하지 않습니다.")).getTime();
		
		if(LocalDateTime.of(time.getDate(), time.getTime()).isBefore(LocalDateTime.now())) {
			throw new IllegalAccessException("타임딜이 종료되었습니다.");
		}
	}
}
