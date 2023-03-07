package com.timedeal.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

@Configuration
@EnableRedisRepositories
@EnableRedisHttpSession
public class RedisConfiguration {

	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.host}")
    private String host;
    
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
		return new LettuceConnectionFactory(configuration);
	}

    @Bean
    public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
    	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
    }
    
    @Profile("local")
    @Bean
    public EmbeddedRedisConfiguration embeddedRedisConfiguration() {
		return new EmbeddedRedisConfiguration(port);
    }

    static class EmbeddedRedisConfiguration {
    	
    	private final RedisServer redisServer;
        
        public EmbeddedRedisConfiguration(int port) {
			this.redisServer = new RedisServer(port);
		}

		@PostConstruct
        public void postConstruct() {
            redisServer.start();
        }

        @PreDestroy
        public void preDestroy() {
            redisServer.stop();
        }
    }
    
}