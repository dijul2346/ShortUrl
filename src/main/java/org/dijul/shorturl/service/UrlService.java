package org.dijul.shorturl.service;

import com.aerospike.client.AerospikeClient;
import org.dijul.shorturl.model.ShortUrl;
import org.dijul.shorturl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UrlService {
    @Qualifier("customAerospikeClient")
    private AerospikeClient aerospikeClient;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    Base62Encoder base62Encoder;


    public String shortUrl(String url) {
        Long uniqueId= sequenceGeneratorService.generateNextId();
        String shortCode=base62Encoder.encode(uniqueId);

        ShortUrl document = new ShortUrl();
        document.setShortCode(shortCode);
        document.setLongUrl(url);
        document.setCreatedDate(LocalDateTime.now());
        urlRepository.save(document);
        return shortCode;
    }

    public ResponseEntity<String> longUrl(String url) {
        ShortUrl shortUrl = urlRepository.findById(Long.parseLong(url)).orElse(null);
        if (shortUrl != null) {
            shortUrl.setClicks(shortUrl.getClicks() + 1);
            urlRepository.save(shortUrl);
            return ResponseEntity.ok(shortUrl.getLongUrl());
        }
        return ResponseEntity.notFound().build();
    }

}
