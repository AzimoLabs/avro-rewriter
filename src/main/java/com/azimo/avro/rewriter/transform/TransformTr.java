package com.azimo.avro.rewriter.transform;

import com.azimo.avro.rewriter.serialize.AvroGenericRecord;
import org.apache.beam.sdk.values.PCollection;

public class TransformTr extends AvroBaseTr {

    @Override
    public PCollection<AvroGenericRecord> expand(PCollection<AvroGenericRecord> input) {
        //Trasnform your data and return here
        return input;
    }

}
