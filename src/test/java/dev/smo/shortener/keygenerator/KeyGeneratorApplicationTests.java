package dev.smo.shortener.keygenerator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;


@SpringBootTest
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class KeyGeneratorApplicationTests {

	@Autowired
	private GenericContainer<?> redisContainer;

	@Test
	void testSomething() {
		String host = redisContainer.getHost();
		Integer port = redisContainer.getMappedPort(6379);

		System.out.println("Redis host: " + host);
		System.out.println("Redis port: " + port);
	}

	@Test
	void contextLoads() {
	}

}
