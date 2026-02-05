package org.dijul.shorturl.service;

import com.aerospike.client.AerospikeClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class SequenceGeneratorTest {

    @Mock
    AerospikeClient aerospikeClient;

    @InjectMocks
    SequenceGeneratorService sequenceGeneratorService;
    void initSequenceTest(){
        sequenceGeneratorService.initSequence();
    }


}
