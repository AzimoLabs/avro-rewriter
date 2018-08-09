package com.azimo.avro.rewriter.transform;

import com.azimo.avro.rewriter.serialize.AvroGenericRecord;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;

/**
 * Base class for avro transform to impose types. Concrete implementation will be loaded based on configuration.
 */
public abstract class AvroBaseTr extends PTransform<PCollection<AvroGenericRecord>, PCollection<AvroGenericRecord>> {

}
