package com.codingdm.mriya.utils;

import java.util.Properties;

/**
 * @program: mriya
 * @description: kafka utils
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:19
 */
public class KafkaConfigUtil {

    public static Properties buildKafkaProps() {

        Properties props = new Properties();

        props.put("bootstrap.servers", "192.168.2.185:9092");
        props.put("zookeeper.connect", "127.0.0.1:2181");
        props.put("group.id", "mriya-dev");
        props.put("auto.offset.reset", "earliest");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
