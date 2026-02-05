package org.dijul.shorturl.model;

import lombok.Data;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.aerospike.mapping.Field;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Document(collection = "urls")
public class ShortUrl {

    @Field("longUrl")
    private String longUrl;

    private Optional<String> customUrl;

    @Id
    private String shortCode;

    private LocalDateTime createdDate = LocalDateTime.now();

    private long clicks = 0;
}

