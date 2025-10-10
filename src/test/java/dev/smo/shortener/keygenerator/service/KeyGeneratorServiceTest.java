package dev.smo.shortener.keygenerator.service;

import dev.smo.shortener.keygenerator.counter.CounterService;
import dev.smo.shortener.keygenerator.util.Base62Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeyGeneratorServiceTest {

    private KeyGeneratorService service;
    private CounterService counterService;

    // Simulate a counter starting from 4783 so first call returns 4784
    private long mockCounter = 4783L;

    @BeforeEach
    void setUp() {
        counterService = mock(CounterService.class);
        when(counterService.getNext()).thenAnswer(invocation -> ++mockCounter);
        service = new KeyGeneratorService(counterService);
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

    @Test
    void testCounterName1StartsAtExpectedValue() {
        when(counterService.getNext("COUNTER_NAME_1")).thenReturn(4784L);

        long id = service.getNextKey("COUNTER_NAME_1");
        assertEquals(4784L, id);
        assertEquals(Base62Encoder.encode(4784L), service.encode(id));
    }

    @Test
    void testCounterName1IncrementsCorrectly() {
        when(counterService.getNext("COUNTER_NAME_1"))
                .thenReturn(4784L, 4785L, 4786L);

        long id1 = service.getNextKey("COUNTER_NAME_1");
        long id2 = service.getNextKey("COUNTER_NAME_1");
        long id3 = service.getNextKey("COUNTER_NAME_1");

        assertEquals(4784L, id1);
        assertEquals(4785L, id2);
        assertEquals(4786L, id3);

        assertEquals(Base62Encoder.encode(id1), service.encode(id1));
        assertEquals(Base62Encoder.encode(id2), service.encode(id2));
        assertEquals(Base62Encoder.encode(id3), service.encode(id3));
    }

    @Test
    void testDifferentCountersAreIndependent() {
        when(counterService.getNext("COUNTER_NAME_1"))
                .thenReturn(4784L, 4785L);
        when(counterService.getNext("COUNTER_NAME_2"))
                .thenReturn(4784L, 4785L);

        long c1_id1 = service.getNextKey("COUNTER_NAME_1");
        long c1_id2 = service.getNextKey("COUNTER_NAME_1");
        long c2_id1 = service.getNextKey("COUNTER_NAME_2");
        long c2_id2 = service.getNextKey("COUNTER_NAME_2");

        assertEquals(4784L, c1_id1);
        assertEquals(4785L, c1_id2);
        assertEquals(4784L, c2_id1);
        assertEquals(4785L, c2_id2);

        assertEquals(Base62Encoder.encode(c1_id1), service.encode(c1_id1));
        assertEquals(Base62Encoder.encode(c2_id1), service.encode(c2_id1));
    }

}



