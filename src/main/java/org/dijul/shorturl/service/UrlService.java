package org.dijul.shorturl.service;

import com.aerospike.client.AerospikeClient;
import org.dijul.shorturl.model.ShortUrl;
import org.dijul.shorturl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

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


    public ResponseEntity<String> shortUrl(String url, Optional<String>customCode) {
        String shortCode;
        try {
            if (customCode.isPresent()) {
                String code = customCode.get();
                boolean exists = urlRepository.existsById(code);
                if (exists) {
//                    throw new RuntimeException("Custom code already exists. Try with a different one.");
                    return new ResponseEntity<>("Custom code already exists. Try with a different one.", HttpStatusCode.valueOf(409));
                } else {
                    shortCode = customCode.get();
                }
            } else {
                Long uniqueId = sequenceGeneratorService.generateNextId();
                shortCode = base62Encoder.encode(uniqueId);
            }

            ShortUrl document = new ShortUrl();
            document.setShortCode(shortCode);
            document.setLongUrl(url);
            document.setCreatedDate(LocalDateTime.now());
            urlRepository.save(document);
            return ResponseEntity.ok(shortCode);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    }

//    public ResponseEntity<String> longUrl(String url) {
//        ShortUrl shortUrl = urlRepository.findById(Long.parseLong(url)).orElse(null);
//        if (shortUrl != null) {
//            shortUrl.setClicks(shortUrl.getClicks() + 1);
//            urlRepository.save(shortUrl);
//            return ResponseEntity.ok(shortUrl.getLongUrl());
//        }
//        return ResponseEntity.notFound().build();
//    }


