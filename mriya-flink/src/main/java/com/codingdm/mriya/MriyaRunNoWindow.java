package com.codingdm.mriya;

import com.codingdm.mriya.aggregate.AggregateMessage;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.filter.MsgFilter;
import com.codingdm.mriya.flatmap.MessageMapFunction;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.sink.GreenplumSink;
import com.codingdm.mriya.trigger.DdlTrigger;
import com.codingdm.mriya.utils.KafkaConfigUtil;
import com.codingdm.mriya.utils.MessageSchema;
import lombok.extern.log4j.Log4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @program: mriya
 * @description: run DEV no window
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:16
 */
@Log4j
public class MriyaRunNoWindow {
    public static void main(String[] args) throws Exception {
        log.info("start mriya");
        String topic = NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_TOPIC);
        final StreamExecutionEnvironment flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = KafkaConfigUtil.buildKafkaProps();
        FlinkKafkaConsumer<Message> kafkaSource = new FlinkKafkaConsumer<>(topic, new MessageSchema(topic), props);
        flinkEnv.addSource(kafkaSource)
                .filter(MsgFilter::filterType)
                .filter(MsgFilter::filter)
                .map(MessageMapFunction.build())
                .addSink(new GreenplumSink());
        flinkEnv.execute("mriya run topic: " + topic);
    }

}
