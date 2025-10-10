package dev.smo.shortener.keygenerator.controller;

import dev.smo.shortener.keygenerator.dto.KeyResponse;
import dev.smo.shortener.keygenerator.service.KeyGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/keys")
public class KeyGeneratorController {

    private final KeyGeneratorService keyGeneratorService;

    public KeyGeneratorController(KeyGeneratorService keyGeneratorService) {
        this.keyGeneratorService = keyGeneratorService;
    }

    @GetMapping("/next")
    public KeyResponse generateKey() {
        long id = keyGeneratorService.getNextKey();
        String key = keyGeneratorService.encode(id);
        log.info(String.format("Key generated: %s, %s", id, key));
        return new KeyResponse(id, key);
    }

    @GetMapping("/next/{name}")
    public KeyResponse generateKey(@PathVariable String name) {
        long id = keyGeneratorService.getNextKey(name);
        String key = keyGeneratorService.encode(id);
        log.info(String.format("Key generated: %s, %s", id, key));
        return new KeyResponse(id, key);
    }

}

