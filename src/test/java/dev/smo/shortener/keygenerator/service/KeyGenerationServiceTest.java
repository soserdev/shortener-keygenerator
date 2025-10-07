package dev.smo.shortener.keygenerator.service;

import dev.smo.shortener.keygenerator.counter.CounterService;
import dev.smo.shortener.keygenerator.util.Base62Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeyGenerationServiceTest {

    private KeyGenerationService service;
    private CounterService counterService;

    // Simulate a counter starting from 4783 so first call returns 4784
    private long mockCounter = 4783L;

    @BeforeEach
    void setUp() {
        counterService = mock(CounterService.class);
        when(counterService.getNext()).thenAnswer(invocation -> ++mockCounter);
        service = new KeyGenerationService(counterService);
    }

    @Test
    void testFirstGeneratedKeyIsCorrect() {
        long id = service.getNextKey();
        String encoded = service.encode(id);

        assertEquals(4784L, id);
        assertEquals(Base62Encoder.encode(4784L), encoded);
    }

    @Test
    void testSequentialKeyValues() {
        long id1 = service.getNextKey(); // 4784
        long id2 = service.getNextKey(); // 4785
        long id3 = service.getNextKey(); // 4786

        assertEquals(4784L, id1);
        assertEquals(4785L, id2);
        assertEquals(4786L, id3);

        assertEquals(Base62Encoder.encode(4784L), service.encode(id1));
        assertEquals(Base62Encoder.encode(4785L), service.encode(id2));
        assertEquals(Base62Encoder.encode(4786L), service.encode(id3));
    }

    @Test
    void testUniqueKeys() {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            long id = service.getNextKey();
            String key = service.encode(id);
            assertTrue(keys.add(key), "Duplicate key found: " + key);
        }
    }
}



