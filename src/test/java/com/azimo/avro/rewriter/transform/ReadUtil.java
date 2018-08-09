package com.azimo.avro.rewriter.transform;

import com.azimo.avro.rewriter.read.ReadAvroFilesAsSpecificRecordsTr;
import com.azimo.avro.rewriter.write.DynamicAvroGenericRecordDestinations;

import java.nio.file.Path;

public class ReadUtil {
    public static String createReadPath(Class<?> messageClass, Path inputDir) {
        return createReadPath(messageClass.getSimpleName(), inputDir);
    }

    public static String createReadPath(String messageType, Path inputDir) {
        StringBuilder sb = new StringBuilder();
        sb.append(inputDir.toAbsolutePath().toString());
        sb.append("/");
        sb.append(messageType);
        sb.append("/");
        sb.append(DynamicAvroGenericRecordDestinations.getTodaysSubDir());
        sb.append(ReadAvroFilesAsSpecificRecordsTr.AVRO_FILES_SUFFIX);
        return sb.toString();
    }
}
