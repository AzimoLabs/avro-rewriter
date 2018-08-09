package com.azimo.avro.rewriter;

import com.azimo.avro.rewriter.config.AvroRewriterPipelineOptions;
import com.azimo.avro.rewriter.config.Constants;
import com.azimo.avro.rewriter.read.ReadAvroFilesTr;
import com.azimo.avro.rewriter.serialize.AvroGenericCoder;
import com.azimo.avro.rewriter.transform.AvroBaseTr;
import com.azimo.avro.rewriter.write.WriteAvroFilesTr;
import com.google.common.collect.Lists;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class AvroRewriterApp {

    public static void main(String[] args) {
        AvroRewriterPipelineOptions options = PipelineOptionsFactory.fromArgs(args).as(AvroRewriterPipelineOptions.class);
        new AvroRewriterApp().run(options);
    }

    public void run(AvroRewriterPipelineOptions options) {
        configureStaticOptions(options);
        Pipeline p = Pipeline.create(options);

        try {
            AvroBaseTr transform = options.getTransformClass().newInstance();
            p.apply(new ReadAvroFilesTr(Lists.newArrayList(createReadPath(options))))
                    .apply("Avro Transform", transform).setCoder(AvroGenericCoder.of())
                    .apply(new WriteAvroFilesTr(options.getBaseOutputPath(), options.getNumberOfShards()));

            p.run();
        } catch (Exception e) {
            throw new RuntimeException("Not able to instantiate transform class: " + options.getTransformClass().getName(), e);
        }
    }

    private String createReadPath(AvroRewriterPipelineOptions options) {
        String baseInputPath = options.getBaseInputPath();
        String messageType = options.getInputMessageType();
        StringBuilder sb = new StringBuilder();
        sb.append(baseInputPath);
        if (!baseInputPath.endsWith("/"))
            sb.append("/");
        sb.append(messageType);
        sb.append("/*");
        sb.append(ReadAvroFilesTr.AVRO_FILES_SUFFIX);
        return sb.toString();
    }

    private void configureStaticOptions(AvroRewriterPipelineOptions options) {
        options.setAppName(Constants.APP_NAME);
    }
}
