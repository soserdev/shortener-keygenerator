package dev.smo.shortener.keygenerator.service;

import dev.smo.shortener.keygenerator.util.Base62Encoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class KeyGeneratorService {

    private final AtomicLong counter;

    // Start at 1000L => g8
    public KeyGeneratorService() {
        this.counter = new AtomicLong(1000L);
    }

    public String getNextKey() {
        long currentValue = counter.getAndIncrement();
        return Base62Encoder.encode(currentValue);
    }
}
