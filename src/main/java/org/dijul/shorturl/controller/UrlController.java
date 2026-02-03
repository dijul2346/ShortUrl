package org.dijul.shorturl.controller;

import org.dijul.shorturl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    UrlService urlService;

    @PostMapping("/{url}")
    public String createShortUrl(@PathVariable String url) {
        return urlService.shortUrl(url);
    }

    @GetMapping("/{url}")
    public ResponseEntity<String> getLongUrl(@PathVariable String url) {
        return urlService.longUrl(url);
    }
}
