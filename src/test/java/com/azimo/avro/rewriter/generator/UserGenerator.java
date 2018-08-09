package com.azimo.avro.rewriter.generator;

import com.azimo.kafka.avro.writer.User;
import com.azimo.kafka.avro.writer.UserDetails;
import com.azimo.kafka.avro.writer.UserType;
import org.apache.avro.generic.GenericData.EnumSymbol;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;

public class UserGenerator {
    private static final String NAME = "TestName";
    private static final String DETAILS_ID = "detailsId";

    public static GenericRecord createUserGenericRecord() {
        GenericRecord user = new Record(User.SCHEMA$);
        user.put("name", NAME);
        GenericEnumSymbol userType = new EnumSymbol(UserType.SCHEMA$, "ADMIN");
        GenericRecord userDetails = new Record(UserDetails.SCHEMA$);
        userDetails.put("detailsId",DETAILS_ID);
        user.put("type", userType);
        user.put("details", userDetails);
        return user;
    }
}
