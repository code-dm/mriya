package com.codingdm.mriya.utils;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.PropertiesConstants;
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

        props.put("bootstrap.servers", NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_BOOTSTRAP_SERVERS));
        props.put("zookeeper.connect", NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_ZOOKEEPER_CONNECT));

        props.put("group.id", NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_GROUP_ID));
        props.put("auto.offset.reset", NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_AUTO_OFFSET_RESET));

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        log.info("get kafka config, config map-> " + props.toString());
        return props;
    }
}
