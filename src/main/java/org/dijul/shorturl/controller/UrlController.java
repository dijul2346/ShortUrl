package org.dijul.shorturl.controller;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.dijul.shorturl.dto.RequestDTO;
import org.dijul.shorturl.model.ShortUrl;
import org.dijul.shorturl.repository.UrlRepository;
import org.dijul.shorturl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Configuration
@RestController
@RequestMapping("/shorturl")
public class UrlController {

    @Autowired
    UrlService urlService;

    @Autowired
    UrlRepository urlRepository;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @PostMapping("/add")
    public String createShortUrl(@RequestBody RequestDTO request) {
        return urlService.shortUrl(request.getLink(), Optional.ofNullable(request.getShortCode()));
    }

    @GetMapping("/view/{url}")
    public ResponseEntity<String> getLongUrl(@PathVariable String url) {
        try {
            ShortUrl shortUrl = urlRepository.findById(url)
                    .orElseThrow(() -> new RuntimeException("Link not found"));
            return ResponseEntity.ok(shortUrl.getLongUrl());
        } catch (RuntimeException e) {
            return ResponseEntity.ok("Link not found");
        }
    }

    @GetMapping("/{url}")
    public Void redirectToLongUrl(@PathVariable String url, ServletResponse servletResponse) {
        try {
            ShortUrl shortUrl = urlRepository.findById(url)
                    .orElseThrow(() -> new RuntimeException("Link not found"));
            String longUrl = shortUrl.getLongUrl();
            if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
                longUrl = "https://" + longUrl;
            }
            try {
                httpServletResponse.sendRedirect(longUrl);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } catch (RuntimeException e) {
            try {
                httpServletResponse.sendError(404, "Not found");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
}
