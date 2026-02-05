package org.dijul.shorturl.service;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
    @Qualifier("customAerospikeClient")
    AerospikeClient aerospikeClient;

    private static final long STARTING_POINT = 8720424L;

    @PostConstruct
    public void initSequence() {
        Key key = new Key(this.namespace, SET_NAME, BIN_NAME);
        WritePolicy createOnlyPolicy = new WritePolicy();
        createOnlyPolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
        try{
            Bin bin= new Bin(BIN_NAME, STARTING_POINT);
            aerospikeClient.put(createOnlyPolicy, key, bin);
            System.out.println("Sequence initialized.");
        } catch (AerospikeException e) {
            System.out.println("Sequence already initialized.");
        }

    }


    public SequenceGeneratorService(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Value("${aerospike.namespace}")
    private String namespace;

    private static final String SET_NAME = "sequences";
    private static final String BIN_NAME = "seq";

    public Long generateNextId(){
        Key key=new Key(this.namespace,SET_NAME,BIN_NAME);
        Bin bin=new Bin(BIN_NAME,1L);

        Record record= aerospikeClient.operate(
                null,
                key,
                Operation.add(bin),
                Operation.get(BIN_NAME)
        );
        return record.getLong(BIN_NAME);
    }


}

