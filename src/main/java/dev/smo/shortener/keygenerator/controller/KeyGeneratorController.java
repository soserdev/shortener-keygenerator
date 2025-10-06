package dev.smo.shortener.keygenerator.controller;

import dev.smo.shortener.keygenerator.service.KeyGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/keys")
public class KeyGeneratorController {

    private final KeyGeneratorService keyGeneratorService;

    public KeyGeneratorController(KeyGeneratorService keyGeneratorService) {
        this.keyGeneratorService = keyGeneratorService;
    }

    @GetMapping("/next")
    public String getNextKey() {
        return keyGeneratorService.getNextKey();
    }
}
