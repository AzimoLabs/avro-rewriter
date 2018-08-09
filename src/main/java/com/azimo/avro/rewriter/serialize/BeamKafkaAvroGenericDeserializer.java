package com.azimo.avro.rewriter.serialize;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.beam.sdk.util.EmptyOnDeserializationThreadLocal;
import org.apache.kafka.common.errors.SerializationException;

import java.io.IOException;
import java.io.InputStream;

public class BeamKafkaAvroGenericDeserializer {
    private final static DecoderFactory DECODER_FACTORY = DecoderFactory.get();
    private final EmptyOnDeserializationThreadLocal<BinaryDecoder> decoder;

    public BeamKafkaAvroGenericDeserializer() {
        decoder = new EmptyOnDeserializationThreadLocal<>();
    }

    public AvroGenericRecord deserialize(InputStream in) {
        try {
            BinaryDecoder decoderInstance = DECODER_FACTORY.directBinaryDecoder(in, decoder.get());
            Schema schema = new Parser().parse(decoderInstance.readString());
            decoder.set(decoderInstance);

            DatumReader<GenericRecord> reader = new GenericDatumReader<>(schema);
            GenericRecord object = reader.read(null, decoderInstance);
            return AvroGenericRecord.of(object);
        } catch (RuntimeException | IOException var15) {
            throw new SerializationException("Error deserializing Avro message", var15);
        }
    }
}
