package dev.smo.shortener.keygenerator.controller;

import dev.smo.shortener.keygenerator.service.KeyGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KeyGeneratorController.class)
class KeyGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KeyGeneratorService keyGeneratorService;

    @Test
    void testGenerateKeyEndpoint() throws Exception {
        // Arrange: mock the service
        long mockId = 4784L;
        String mockKey = "1cS";

        when(keyGeneratorService.getNextKey()).thenReturn(mockId);
        when(keyGeneratorService.encode(mockId)).thenReturn(mockKey);

        // Act & Assert
        mockMvc.perform(get("/api/keys/next"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockId))
                .andExpect(jsonPath("$.key").value(mockKey));
    }
}
