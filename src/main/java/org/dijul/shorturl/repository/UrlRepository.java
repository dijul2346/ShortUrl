package org.dijul.shorturl.repository;
import org.dijul.shorturl.model.ShortUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends AerospikeRepository<ShortUrl,String>{

}
