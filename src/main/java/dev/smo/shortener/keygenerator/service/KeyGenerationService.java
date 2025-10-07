package dev.smo.shortener.keygenerator.service;

import dev.smo.shortener.keygenerator.counter.CounterService;
import dev.smo.shortener.keygenerator.util.Base62Encoder;
import org.springframework.stereotype.Service;

@Service
public class KeyGenerationService {

    private final CounterService counterService;

    public KeyGenerationService(CounterService counterService) {
        this.counterService = counterService;
    }

    /**
     * Returns the next counter value from the source (e.g., Redis).
     */
    public long getNextKey() {
        return counterService.getNext();
    }

    /**
     * Encodes a numeric key to Base62.
     */
    public String encode(long value) {
        return Base62Encoder.encode(value);
    }
}





