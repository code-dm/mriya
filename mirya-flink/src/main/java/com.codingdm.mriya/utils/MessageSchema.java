package com.codingdm.mriya.utils;

import com.codingdm.mriya.model.Message;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * @program: mriya
 * @description: kafka to convert Message obj
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:28
 */
public class MessageSchema implements DeserializationSchema<Message>, SerializationSchema<Message> {
    @Override
    public Message deserialize(byte[] bytes) {
        return Message.buildMessage(new String(bytes));
    }

    @Override
    public boolean isEndOfStream(Message message) {
        return false;
    }

    @Override
    public byte[] serialize(Message message) {
        return new byte[0];
    }

    @Override
    public TypeInformation<Message> getProducedType() {
        return TypeInformation.of(Message.class);
    }
}
