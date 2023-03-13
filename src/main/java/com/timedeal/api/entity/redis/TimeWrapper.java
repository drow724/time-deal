package com.timedeal.api.entity.redis;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import com.timedeal.api.entity.Product;
import com.timedeal.api.entity.Time;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash("time")
@NoArgsConstructor
public class TimeWrapper implements Serializable {

	private static final long serialVersionUID = 4918812627882665983L;

	@Id
	private String id;
	
	private Time time;

	public TimeWrapper(Product product, Time time) {
		this.id = product.getId().toString();
		this.time = time;
	}

	public void update(Time time) {
		this.time = time;
	}
}