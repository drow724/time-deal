package com.timedeal.common.aop;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.timedeal.api.entity.redis.TimeWrapper;
import com.timedeal.api.http.request.OrderRequest;
import com.timedeal.api.repository.redis.TimeRedisRepository;
import com.timedeal.common.util.LocalDateTimeUtil;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class TimeAop {

	private static final int WAIT_TIME = 1;
	private static final int LEASE_TIME = 2;
	
	private final RedissonClient redissonClient;

	private final TimeRedisRepository timeRedisRepository;

	@Before("@annotation(com.timedeal.common.annotation.Time)")
	public void isBefore(JoinPoint joinPoint) throws IllegalAccessException {
		
		System.out.println("여");
		Object[] args = joinPoint.getArgs();
		
		OrderRequest request = new OrderRequest();
		
		for(Object arg : args) {
			if(arg instanceof OrderRequest) {
				request = (OrderRequest) arg;
			}
		}
		
		RLock lock = redissonClient.getLock("time");
		
        try {
            if (!(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))) {
                throw new RuntimeException("Rock fail!");
            }
           TimeWrapper wrapper = timeRedisRepository.findById(request.getProductId().toString()).orElseThrow(() -> new NoSuchElementException("해당 상품의 타임딜이 존재하지 않습니다."));
           LocalDateTime ttl = LocalDateTime.of(wrapper.getTime().getDate(), wrapper.getTime().getTime());
           if(LocalDateTimeUtil.now().isAfter(ttl)) {
        	   throw new IllegalAccessException("타임딜 시간이 경과하였습니다.");
           }
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            lock.unlock();
        }
	}
}
