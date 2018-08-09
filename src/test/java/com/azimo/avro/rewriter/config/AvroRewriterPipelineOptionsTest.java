package com.azimo.avro.rewriter.config;

import com.azimo.avro.rewriter.transform.AvroBaseTr;
import com.azimo.avro.rewriter.transform.TransformTr;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AvroRewriterPipelineOptionsTest {

    @Test
    public void testClass() {
        String [] args = new String[]{"--transformClass=com.azimo.avro.rewriter.transform.TransformTr"};
        AvroRewriterPipelineOptions options = PipelineOptionsFactory.fromArgs(args).as(AvroRewriterPipelineOptions.class);
        Class<? extends AvroBaseTr> transformClass = options.getTransformClass();
        assertThat(transformClass).isEqualTo(TransformTr.class);
    }

}