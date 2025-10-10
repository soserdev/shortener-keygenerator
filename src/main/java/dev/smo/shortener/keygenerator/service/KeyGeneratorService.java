package dev.smo.shortener.keygenerator.service;

import dev.smo.shortener.keygenerator.counter.CounterService;
import dev.smo.shortener.keygenerator.util.Base62Encoder;
import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorService {

    private final CounterService counterService;

    public KeyGeneratorService(CounterService counterService) {
        this.counterService = counterService;
    }

    /**
     * Returns the next counter value from the source (e.g., Redis).
     */
    public long getNextKey() {
        return counterService.getNext();
    }

    /**
     * Returns the next counter value from the source for a specific counter (e.g., Redis).
     */
    public long getNextKey(String counterName) {
        return counterService.getNext(counterName);
    }

    /**
     * Encodes a numeric key to Base62.
     */
    public String encode(long value) {
        return Base62Encoder.encode(value);
    }
}





