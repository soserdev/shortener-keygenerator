package dev.smo.shortener.keygenerator.controller;

import dev.smo.shortener.keygenerator.dto.KeyResponse;
import dev.smo.shortener.keygenerator.service.KeyGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keys")
public class KeyGeneratorController {

    private static final Logger log = LoggerFactory.getLogger(KeyGeneratorController.class);

    private final KeyGeneratorService keyGeneratorService;

    public KeyGeneratorController(KeyGeneratorService keyGeneratorService) {
        this.keyGeneratorService = keyGeneratorService;
    }

    @GetMapping("/next")
    public KeyResponse generateKey() {
        long id = keyGeneratorService.getNextKey();
        String key = keyGeneratorService.encode(id);
        log.info("Key generated: {}, {}", id, key);
        return new KeyResponse(id, key);
    }

    @GetMapping("/next/{name}")
    public KeyResponse generateKey(@PathVariable String name) {
        long id = keyGeneratorService.getNextKey(name);
        String key = keyGeneratorService.encode(id);
        log.info("Key generated: {}, {} for name {}", id, key, name);
        return new KeyResponse(id, key);
    }

}

