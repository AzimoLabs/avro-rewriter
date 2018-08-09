package com.azimo.avro.rewriter.serialize;

import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.InputStream;
import java.io.OutputStream;

public class AvroGenericCoder extends CustomCoder<AvroGenericRecord> {
    private transient BeamKafkaAvroGenericDeserializer deserializer;
    private transient BeamKafkaAvroGenericSerializer serializer;

    public static AvroGenericCoder of() {
        return new AvroGenericCoder();
    }

    protected AvroGenericCoder() {}

    private BeamKafkaAvroGenericDeserializer getDeserializer() {
        if (deserializer == null) {
            deserializer = new BeamKafkaAvroGenericDeserializer();
        }
        return deserializer;
    }

    private BeamKafkaAvroGenericSerializer getSerializer() {
        if (serializer == null) {
            serializer = new BeamKafkaAvroGenericSerializer();
        }
        return serializer;
    }

    @Override
    public void encode(AvroGenericRecord record, OutputStream outStream) {
        getSerializer().serialize(record, outStream);
    }

    @Override
    public AvroGenericRecord decode(InputStream inStream) {
        return getDeserializer().deserialize(inStream);
    }

    @Override
    public void verifyDeterministic() {
        //We operate only on generic records using schema registry so we assume everything is deterministic
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
