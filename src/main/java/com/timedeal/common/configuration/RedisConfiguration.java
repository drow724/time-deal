package com.timedeal.common.configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import redis.embedded.RedisServer;

@Configuration
@EnableRedisRepositories
@EnableRedisHttpSession
@RequiredArgsConstructor
public class RedisConfiguration {

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.host}")
	private String host;

	private final RedisProperties redisProperties;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
		return new RedissonConnectionFactory();
	}

	@Bean
	public RedissonClient redissonClientSingle() throws IOException {
		RedissonClient redisson = null;
		Config config = new Config();
		final Codec codec = new StringCodec(); // redis-cli에서 보기 위해
		config.setCodec(codec);
		config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
				.setConnectionPoolSize(100); // pool size는 custom
		redisson = Redisson.create(config);

		return redisson;
	}

	@Bean
	public ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())));
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())));

		ObjectMapper mapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).registerModules(module);

		return mapper;
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}

	@Profile({ "local", "test" })
	@Bean
	public EmbeddedRedisConfiguration embeddedRedisConfiguration() {
		return new EmbeddedRedisConfiguration(port);
	}

	static class EmbeddedRedisConfiguration {

		private final RedisServer redisServer;

		public EmbeddedRedisConfiguration(int port) {
			this.redisServer = new RedisServer(port);
		}

		public void redisServer() {
			if(redisServer.isActive()) {
				redisServer.stop();
			}
			redisServer.start();
		}

		@PreDestroy
		public void preDestroy() {
			redisServer.stop();
		}
	}

}