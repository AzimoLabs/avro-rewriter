package com.azimo.avro.rewriter.config;

import com.azimo.avro.rewriter.transform.AvroBaseTr;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

public interface AvroRewriterPipelineOptions extends DataflowPipelineOptions {
	@Description("Base write path (on prod path should point to Gcs bucket")
	@Validation.Required
	String getBaseOutputPath();
	void setBaseOutputPath(String value);

	@Description("Base read path (on prod path should point to Gcs bucket")
	@Validation.Required
	String getBaseInputPath();
	void setBaseInputPath(String value);

	@Description("Number of shards per event type")
	@Default.Integer(3)
	int getNumberOfShards();
	void setNumberOfShards(int value);

	@Description("Input message type (folder on gcs)")
	@Validation.Required
	String getInputMessageType();
	void setInputMessageType(String value);

    @Description("Get transform class")
    @Validation.Required
    Class<? extends AvroBaseTr> getTransformClass();
    void setTransformClass(Class<? extends AvroBaseTr> value);
}
