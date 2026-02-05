package org.dijul.shorturl.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.dijul.shorturl.dto.RequestDTO;
import org.dijul.shorturl.model.ShortUrl;
import org.dijul.shorturl.repository.UrlRepository;
import org.dijul.shorturl.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Configuration
@ExtendWith(MockitoExtension.class)
public class UrlControllerTests {

    @Mock
    UrlService urlService;

    @Mock
    UrlRepository urlRepository;

    @Mock
    private ShortUrl shortUrl;

    @Mock
    private RequestDTO request;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private UrlController urlController;

    @BeforeEach
    void setUp() {
        shortUrl=new ShortUrl();
        shortUrl.setShortCode("exmpl");
        shortUrl.setLongUrl("https://example.com");
        request=new RequestDTO();
        request.setLink("https://example.com");
        request.setShortCode("exmpl");
    }

    @Test
    void testCreateShortUrl() {
        when(urlService.shortUrl(request.getLink(),Optional.ofNullable(request.getShortCode()))).thenReturn("success_code");
        String result = urlController.createShortUrl(request);
        assertEquals("success_code", result);
    }

    @Test
    void  testGetLongUrl() {
        when(urlRepository.findById("exmpl")).thenReturn(Optional.of(shortUrl));
        var response = urlController.getLongUrl("exmpl");
        assertEquals("https://example.com", response.getBody());
    }

    @Test
    void testGetLongUrlNotFound() {
        when(urlRepository.findById("invalid")).thenReturn(Optional.empty());
        var response = urlController.getLongUrl("invalid");
        assertEquals("Link not found", response.getBody());
    }

    @Test
    void testRedirectToLongUrl() {
        when(urlRepository.findById("exmpl")).thenReturn(Optional.of(shortUrl));
        urlController.redirectToLongUrl("exmpl", null);
    }




}
