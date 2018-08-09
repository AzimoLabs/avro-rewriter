package com.azimo.avro.rewriter.serialize;

import com.azimo.avro.rewriter.generator.UserGenerator;
import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.testing.CoderProperties;
import org.junit.Before;
import org.junit.Test;

public class AvroGenericCoderTest {
    private AvroGenericCoder coder;

    @Before
    public void before() {
        coder = AvroGenericCoder.of();
    }

    @Test
    public void serializeCoder() {
        CoderProperties.coderSerializable(coder);
    }

    @Test
    public void encode() throws Exception {
        GenericRecord record = UserGenerator.createUserGenericRecord();
        AvroGenericRecord avroGenericRecord = AvroGenericRecord.of(record);
        CoderProperties.coderDecodeEncodeEqual(coder, avroGenericRecord);
    }
}