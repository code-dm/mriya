package com.codingdm.mriya;

import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.utils.KafkaConfigUtil;
import com.codingdm.mriya.utils.MessageSchema;
import lombok.extern.log4j.Log4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @program: mriya
 * @description: run DEV
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:16
 */
@Log4j
public class MriyaDev {
    public static void main(String[] args) throws Exception {

//        String topic = "mriya";
        String topic = "canal224";
        final StreamExecutionEnvironment flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = KafkaConfigUtil.buildKafkaProps();
        FlinkKafkaConsumer<Message> kafkaSource = new FlinkKafkaConsumer<>(topic, new MessageSchema(topic), props);

        flinkEnv
                .addSource(kafkaSource)
//                .flatMap(FlatMapMessage.build())
                .print();


        flinkEnv.execute("run dev");
    }

}
