package dev.smo.shortener.keygenerator.service;

import dev.smo.shortener.keygenerator.util.Base62Encoder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorServiceTest {

    @Test
    void testFirstGeneratedKeyIsCorrect() {
        KeyGeneratorService service = new KeyGeneratorService();

        String firstKey = service.getNextKey();
        String expected = Base62Encoder.encode(1000L);

        assertEquals(expected, firstKey, "First generated key should be Base62 of 1000");
    }

    @Test
    void testKeysAreSequential() {
        KeyGeneratorService service = new KeyGeneratorService();

        String key1 = service.getNextKey(); // 1000
        String key2 = service.getNextKey(); // 1001
        String key3 = service.getNextKey(); // 1002

        assertEquals(Base62Encoder.encode(1000L), key1);
        assertEquals(Base62Encoder.encode(1001L), key2);
        assertEquals(Base62Encoder.encode(1002L), key3);
    }

    @Test
    void testKeysAreUnique() {
        KeyGeneratorService service = new KeyGeneratorService();
        Set<String> keys = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            String key = service.getNextKey();
            assertTrue(keys.add(key), "Key should be unique: " + key);
        }
    }

    @Test
    void testThreadSafety() throws InterruptedException, ExecutionException {
        KeyGeneratorService service = new KeyGeneratorService();
        int threadCount = 20;
        int keysPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        Set<String> allKeys = ConcurrentHashMap.newKeySet();
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            tasks.add(() -> {
                for (int j = 0; j < keysPerThread; j++) {
                    allKeys.add(service.getNextKey());
                }
                return null;
            });
        }

        List<Future<Void>> futures = executor.invokeAll(tasks);
        for (Future<Void> future : futures) {
            future.get(); // Ensure no exceptions
        }

        executor.shutdown();

        int expectedSize = threadCount * keysPerThread;
        assertEquals(expectedSize, allKeys.size(), "All generated keys should be unique even under concurrency");
    }
}
