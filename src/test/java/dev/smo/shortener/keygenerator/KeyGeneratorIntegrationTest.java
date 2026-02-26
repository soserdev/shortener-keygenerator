package dev.smo.shortener.keygenerator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class KeyGeneratorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGenerateKeyWithRedisContainer() throws Exception {
        mockMvc.perform(get("/api/keys/next"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.key").isString());
    }


    @Test
    void testSequentialKeysAreIncrementing() throws Exception {
        long firstId = extractIdFromResponse(mockMvc.perform(get("/api/keys/next")));
        long secondId = extractIdFromResponse(mockMvc.perform(get("/api/keys/next")));

        assertEquals(firstId + 1, secondId, "IDs should increment sequentially");
    }

    private long extractIdFromResponse(org.springframework.test.web.servlet.ResultActions resultActions) throws Exception {
        String json = resultActions.andReturn().getResponse().getContentAsString();
        return Long.parseLong(json.replaceAll(".*\"id\":(\\d+).*", "$1"));
    }
}


