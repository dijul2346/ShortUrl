package org.dijul.shorturl.service;

import org.dijul.shorturl.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UrlServiceTests {

    @Mock
    SequenceGeneratorService sequenceGenerator;

    @Mock
    Base62Encoder base62Encoder;

    @Mock
    UrlRepository repo;


    @InjectMocks
    UrlService urlService;

    @Test
    void testShortUrlGeneration() {
        String customCode = "mycode";
        String longUrl = "https://example.com";

        when(repo.existsById(customCode)).thenReturn(false);
        ResponseEntity<String> response = urlService.shortUrl(longUrl, Optional.of(customCode));
        assertEquals(response.getBody(), customCode);
    }

    @Test
    void testDuplicateCustomecode() {
        String longUrl = "https://example.com";
        String customCode = "mycode";
        when(repo.existsById(customCode)).thenReturn(true);
        ResponseEntity<String> response = urlService.shortUrl(longUrl, Optional.of(customCode));
        assertEquals(response.getBody(), "Custom code already exists. Try with a different one.");

    }

    @Test
    void noCustomCode(){
        String longUrl = "https://example.com";
        Long uniqueId = 123L;
        String shortCode = "abc123";

        when(sequenceGenerator.generateNextId()).thenReturn(uniqueId);
        when(base62Encoder.encode(uniqueId)).thenReturn(shortCode);

        ResponseEntity<String> response = urlService.shortUrl(longUrl, Optional.empty());
        assertEquals(response.getBody(), shortCode);
    }

}
