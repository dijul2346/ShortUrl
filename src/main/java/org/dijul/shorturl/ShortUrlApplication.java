package org.dijul.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;

@SpringBootApplication
@EnableAerospikeRepositories(basePackages = "org.dijul.shorturl.repository")
public class ShortUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApplication.class, args);
    }

}
