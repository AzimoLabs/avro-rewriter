package com.azimo.avro.rewriter.serialize;

import org.apache.avro.generic.GenericRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AvroGenericRecord {
    public GenericRecord record;

    public AvroGenericRecord(GenericRecord record) {
        this.record = record;
    }

    public static AvroGenericRecord of(GenericRecord record) {
        return new AvroGenericRecord(record);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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
