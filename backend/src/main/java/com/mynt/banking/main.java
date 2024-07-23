package com.mynt.banking;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class main {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(main.class, args);
	}

	@Component
	@Configuration
	@AllArgsConstructor
	public static class RedisTestRunner implements CommandLineRunner {

		private StringRedisTemplate stringRedisTemplate;

		@Override
		public void run(String... args) throws Exception {
			System.out.println("Testing Redis connection...");

			// Test writing to Redis
			String key = "test-key";
			String value = "test-value";
			stringRedisTemplate.opsForValue().set(key, value);

			// Test reading from Redis
			String retrievedValue = stringRedisTemplate.opsForValue().get(key);

			if (value.equals(retrievedValue)) {
				System.out.println("Redis connection successful!");
				System.out.println("Stored value: " + retrievedValue);
			} else {
				System.out.println("Redis connection failed.");
			}
		}
	}
}
