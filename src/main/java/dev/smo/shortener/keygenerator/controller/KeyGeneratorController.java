package dev.smo.shortener.keygenerator.controller;

import dev.smo.shortener.keygenerator.dto.KeyResponse;
import dev.smo.shortener.keygenerator.service.KeyGenerationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keys")
public class KeyGeneratorController {

    private final KeyGenerationService keyGenerationService;

    public KeyGeneratorController(KeyGenerationService keyGenerationService) {
        this.keyGenerationService = keyGenerationService;
    }

    @GetMapping("/next")
    public KeyResponse generateKey() {
        long id = keyGenerationService.getNextKey();
        String key = keyGenerationService.encode(id);
        return new KeyResponse(id, key);
    }
}

