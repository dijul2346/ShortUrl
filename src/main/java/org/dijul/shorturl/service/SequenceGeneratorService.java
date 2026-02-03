package org.dijul.shorturl.service;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Operation;
import com.aerospike.client.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
    @Qualifier("customAerospikeClient")
    AerospikeClient aerospikeClient;


    public SequenceGeneratorService(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Value("${aerospike.namespace}")
    private String namespace;

    private static final String SET_NAME = "sequences";
    private static final String COUNTER_KEY = "url_id_sequence";
    private static final String BIN_NAME = "seq";

    public Long generateNextId(){
        Key key=new Key(this.namespace,SET_NAME,BIN_NAME);
        Bin incrementBin=new Bin(BIN_NAME,1);

        Record record=aerospikeClient.operate(null,key, Operation.add(incrementBin), Operation.get(BIN_NAME));
        Long binName= record.getLong(BIN_NAME);
        return binName;
    }


}

