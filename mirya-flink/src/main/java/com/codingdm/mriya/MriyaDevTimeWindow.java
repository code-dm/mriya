package com.codingdm.mriya;

import com.codingdm.mriya.aggregate.AggregateMessage;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.trigger.DdlTrigger;
import com.codingdm.mriya.utils.KafkaConfigUtil;
import com.codingdm.mriya.utils.MessageSchema;
import lombok.extern.log4j.Log4j;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @program: mriya
 * @description: run DEV
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:16
 */
@Log4j
public class MriyaDevTimeWindow {
    public static void main(String[] args) throws Exception {

//        String topic = "mriya";
        String topic = "canal224";
        final StreamExecutionEnvironment flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = KafkaConfigUtil.buildKafkaProps();
        FlinkKafkaConsumer<Message> kafkaSource = new FlinkKafkaConsumer<>(topic, new MessageSchema(topic), props);

        WindowedStream<Message, Tuple, TimeWindow> targetTable = flinkEnv
                .addSource(kafkaSource)
                .keyBy("targetTable")
                .timeWindow(Time.seconds(5))
                .trigger(DdlTrigger.build());
        SingleOutputStreamOperator<Message> process = targetTable

                .aggregate(AggregateMessage.build())

                .process(new ProcessFunction<Message, Message>() {
                    @Override
                    public void processElement(Message message, Context context, Collector<Message> collector) throws Exception {
                        System.out.println("---------------processElement-----------------");
                        System.out.println(message.toJsonString());
                    }
                });


        flinkEnv.execute("run dev");
    }

}
