package com.codingdm.mriya.utils;

import lombok.extern.log4j.Log4j;

import java.util.Properties;

/**
 * @program: mriya
 * @description: kafka utils
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:19
 */
@Log4j
public class KafkaConfigUtil {

    public static Properties buildKafkaProps() {

        Properties props = new Properties();

//        props.put("bootstrap.servers", "192.168.2.185:9092");
//        props.put("zookeeper.connect", "127.0.0.1:2181");

        props.put("bootstrap.servers", "10.168.2.224:9092");
        props.put("zookeeper.connect", "10.168.2.224:2181");

        props.put("group.id", "mriya-dev");
        props.put("auto.offset.reset", "earliest");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        log.info("get kafka config, config map-> " + props.toString());
        return props;
    }
}
