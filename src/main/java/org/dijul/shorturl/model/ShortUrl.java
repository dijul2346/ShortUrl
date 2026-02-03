package org.dijul.shorturl.model;

import lombok.Data;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.aerospike.mapping.Field;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Document(collection = "urls")
public class ShortUrl {

    @Field("longUrl")
    private String longUrl;

    @Id
    private String shortCode;

    private LocalDateTime createdDate = LocalDateTime.now();

    private long clicks = 0;
}

