package com.timedeal.api.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.timedeal.api.entity.redis.ProductWrapper;

public interface ProductRedisRepository extends PagingAndSortingRepository<ProductWrapper, String>, CrudRepository<ProductWrapper, String> {

}
