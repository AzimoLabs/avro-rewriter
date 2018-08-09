package com.azimo.avro.rewriter.write;

import com.azimo.avro.rewriter.serialize.AvroGenericRecord;
import com.azimo.avro.rewriter.config.Constants;
import com.azimo.avro.rewriter.serialize.AvroDestination;
import org.apache.beam.sdk.io.AvroIO;
import org.apache.beam.sdk.io.FileSystems;
import org.apache.beam.sdk.io.WriteFilesResult;
import org.apache.beam.sdk.io.fs.ResourceId;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;

public class WriteAvroFilesTr extends PTransform<PCollection<AvroGenericRecord>, WriteFilesResult<AvroDestination>> {
    private String baseDir;
    private int numberOfShards;

    public WriteAvroFilesTr(String baseDir, int numberOfShards) {
        this.baseDir = baseDir;
        this.numberOfShards = numberOfShards;
    }

    @Override
    public WriteFilesResult<AvroDestination> expand(PCollection<AvroGenericRecord> input) {
        ResourceId tempDir = getTempDir(baseDir);
        return input.apply("Write to avro files", AvroIO.<AvroGenericRecord>writeCustomTypeToGenericRecords()
                .withTempDirectory(tempDir)
                .withWindowedWrites()
                .withNumShards(numberOfShards)
                .to(new DynamicAvroGenericRecordDestinations(baseDir, Constants.FILE_EXTENSION))
        );
    }

    private ResourceId getTempDir(String baseDir) {
        return FileSystems.matchNewResource(baseDir + "/temp", true);
    }
}
