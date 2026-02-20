package dev.smo.shortener.keygenerator.counter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCounterService implements CounterService {

    private static final String COUNTER_PREFIX = "keygen:counter:";
    private static final String COUNTER_DEFAULT_NAME = "default";
    private static final long START_VALUE = 4784L;

    private final StringRedisTemplate redisTemplate;

    public RedisCounterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long getNext() {
        return getNext(COUNTER_DEFAULT_NAME);
    }

    @Override
    public long getNext(String counterName) {
        String redisKey = COUNTER_PREFIX + counterName;

        // Atomically initialize the key to START_VALUE - 1 if it doesn't exist
        redisTemplate.opsForValue().setIfAbsent(redisKey, String.valueOf(START_VALUE - 1));

        // Now increment atomically and return the next value
        return redisTemplate.opsForValue().increment(redisKey);
    }
}

