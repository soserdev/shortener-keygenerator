package dev.smo.shortener.keygenerator.counter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCounterService implements CounterService {

    private static final String COUNTER_KEY = "keygen:counter";
    private static final long DEFAULT_START = 4784L;

    private final StringRedisTemplate redisTemplate;

    public RedisCounterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        initializeIfNeeded();
    }

    private void initializeIfNeeded() {
        Boolean hasKey = redisTemplate.hasKey(COUNTER_KEY);
        if (hasKey == null || !hasKey) {
            redisTemplate.opsForValue().set(COUNTER_KEY, String.valueOf(DEFAULT_START));
        }
    }

    @Override
    public long getNext() {
        Long nextVal = redisTemplate.opsForValue().increment(COUNTER_KEY);
        if (nextVal == null) {
            throw new IllegalStateException("Failed to increment Redis counter");
        }
        return nextVal;
    }
}

