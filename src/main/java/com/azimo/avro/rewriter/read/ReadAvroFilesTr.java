package com.azimo.avro.rewriter.read;

import com.azimo.avro.rewriter.config.Constants;
import com.azimo.avro.rewriter.serialize.AvroGenericCoder;
import com.azimo.avro.rewriter.serialize.AvroGenericRecord;
import org.apache.avro.Schema;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.AvroIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.util.List;

public class ReadAvroFilesTr extends PTransform<PBegin, PCollection<AvroGenericRecord>> {
    public static final String AVRO_FILES_SUFFIX = Constants.AVRO_FILE_NAME_PREFIX + "-*.avro";
    private List<String> paths;

    public ReadAvroFilesTr(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public PCollection<AvroGenericRecord> expand(PBegin p) {
        return p.apply(Create.of(paths)).setCoder(StringUtf8Coder.of())
                .apply(AvroIO.parseAllGenericRecords(AvroGenericRecord::of)
                        .withCoder(AvroGenericCoder.of()));
    }
}
