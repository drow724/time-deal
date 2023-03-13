package com.timedeal.api.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.timedeal.api.entity.redis.TimeWrapper;

public interface TimeRedisRepository extends PagingAndSortingRepository<TimeWrapper, String>, CrudRepository<TimeWrapper, String> {

}