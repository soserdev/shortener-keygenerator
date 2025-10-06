package dev.smo.shortener.keygenerator;

import org.springframework.boot.SpringApplication;

public class TestKeyGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.from(KeyGeneratorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
