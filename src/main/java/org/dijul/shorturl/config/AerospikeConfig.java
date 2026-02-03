package org.dijul.shorturl.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;


import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableAerospikeRepositories(basePackages = "org.dijul.url_shortner.repository")
public class AerospikeConfig extends AbstractAerospikeDataConfiguration {

    @Value("${aerospike.hosts}")
    private String hosts;

    @Value("${aerospike.port}")
    private int port;

    @Value("${aerospike.namespace}")
    private String namespace;

    @PostConstruct
    public void init() {
        System.out.println(">>> AerospikeConfig loaded");
    }

    @Bean(
            name = "customAerospikeClient",
            destroyMethod = "close"
    )
    public AerospikeClient aerospikeClient() {
        System.out.println(">>> AerospikeClient created");
        ClientPolicy policy = new ClientPolicy();
        policy.failIfNotConnected = true;
        return new AerospikeClient(policy, new Host(hosts, port));
    }

    @Override
    protected Collection<Host> getHosts() {
        return Collections.singleton(new Host(hosts, port));
    }

    @Override
    protected String nameSpace() {
        return namespace;
    }
}
