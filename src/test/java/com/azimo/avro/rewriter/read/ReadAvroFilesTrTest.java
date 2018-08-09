package com.azimo.avro.rewriter.read;

import com.azimo.avro.rewriter.generator.UserGenerator;
import com.azimo.avro.rewriter.serialize.AvroGenericCoder;
import com.azimo.avro.rewriter.serialize.AvroGenericRecord;
import com.azimo.avro.rewriter.transform.ReadUtil;
import com.azimo.avro.rewriter.write.WriteAvroFilesTr;
import com.google.common.collect.Lists;
import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadAvroFilesTrTest {
    private static final Path INPUT_DIR = Paths.get("target/input/avrotest/");

    @Before
    public void before() throws IOException {
        FileUtils.deleteDirectory(INPUT_DIR.toFile());
    }

    @Test
    public void test() {
        //given
        Pipeline write = createPipeline();
        GenericRecord user = UserGenerator.createUserGenericRecord();
        List<AvroGenericRecord> expectedRecords = createExpectedRecords(user);
        GenericRecord expectedGenericRecord = expectedRecords.get(0).record;
        String expectedName = (String) expectedGenericRecord.get("name");

        //when
        write.apply(Create.of(expectedRecords).withCoder(AvroGenericCoder.of()))
                .apply(new WriteAvroFilesTr(INPUT_DIR.toAbsolutePath().toString(), 1));
        write.run().waitUntilFinish();

        //then
        Pipeline read = createPipeline();
        List<String> paths = Lists.newArrayList(ReadUtil.createReadPath("User", INPUT_DIR));
        PCollection<AvroGenericRecord> records = read.apply(new ReadAvroFilesTr(Lists.newArrayList(paths)));
        PAssert.that(records).satisfies(actual -> {
            Iterator<AvroGenericRecord> it = actual.iterator();
            assertThat(it.hasNext()).isTrue();
            GenericRecord actualRecord = it.next().record;
            assertThat(actualRecord.get("name")).isEqualTo(expectedName);
            assertThat(it.hasNext()).isFalse();
            return null;
        });
        read.run().waitUntilFinish();
    }

    private List<AvroGenericRecord> createExpectedRecords(GenericRecord... genericRecords) {
        return Arrays.stream(genericRecords)
                .map(g -> AvroGenericRecord.of(g))
                .collect(Collectors.toList());
    }

    private TestPipeline createPipeline() {
        return TestPipeline.create().enableAbandonedNodeEnforcement(false);
    }

}