package com.codingdm.mriya;

import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.trigger.DdlTrigger;
import com.codingdm.mriya.utils.KafkaConfigUtil;
import com.codingdm.mriya.utils.MessageSchema;
import lombok.extern.log4j.Log4j;
import org.apache.flink.streaming.api.datastream.AllWindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
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
public class MriyaDev {
    public static void main(String[] args) throws Exception{

        String topic = "mriya";
        final StreamExecutionEnvironment flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = KafkaConfigUtil.buildKafkaProps();
        FlinkKafkaConsumer<Message> kafkaSource = new FlinkKafkaConsumer<>(topic, new MessageSchema(), props);

        AllWindowedStream<Message, TimeWindow> trigger = flinkEnv
                .addSource(kafkaSource)
                .timeWindowAll(Time.seconds(5))
                .trigger(DdlTrigger.build());

        trigger.process(new ProcessAllWindowFunction<Message, Object, TimeWindow>() {

                    @Override
                    public void process(Context context, Iterable<Message> iterable, Collector<Object> collector) throws Exception {
                        System.out.println("-------------process-------------");
                        iterable.forEach(System.out::println);
                    }
                });
        flinkEnv.execute("run dev");
    }

}
