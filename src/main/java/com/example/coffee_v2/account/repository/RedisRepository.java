package com.example.coffee_v2.account.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private String key(LocalDate date) {
        return "coffee:buyer" + date;
    }

    public Optional<Long> findTodayBuyer(LocalDate date){
        String key = key(date);
        String value = redisTemplate.opsForValue().get(key);

        return value == null ? Optional.empty() : Optional.of(Long.parseLong(value));
    }

    public boolean saveTodayBuyer(LocalDate date, Long memberId, Duration ttl) {
        String key = key(date);
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, String.valueOf(memberId), ttl);
        return Boolean.TRUE.equals(success);
    }
}
