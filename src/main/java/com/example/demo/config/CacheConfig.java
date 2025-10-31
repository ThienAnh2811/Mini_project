package com.example.demo.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("employeeCountCache") {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(
                        name,
                        new ConcurrentHashMap<>(16),
                        false
                );
            }
        };
    }

    @Bean
    public ScheduledExecutorService cacheEvictScheduler() {
        return Executors.newScheduledThreadPool(1);
    }
}