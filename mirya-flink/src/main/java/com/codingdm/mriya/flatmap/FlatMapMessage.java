//package com.codingdm.mriya.flatmap;
//
//import com.codingdm.mriya.model.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.flink.api.common.functions.FlatMapFunction;
//import org.apache.flink.util.Collector;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author wudongming1
// * @email dongming1.wu@genscript.com
// * @Date 6/3/2020 4:45 PM
// **/
//@Slf4j
//public class FlatMapMessage implements FlatMapFunction<Message, Message> {
//    @Override
//    public void flatMap(Message message, Collector<Message> collector) throws Exception {
//        List<Map<String, String>> data = message.getData();
//        if(data != null){
//            data.forEach(d-> {
//                message.setDataMap(d);
//                message.setData(null);
//                collector.collect(message);
//            });
//        }else {
//            collector.collect(message);
//        }
//    }
//
//    public static FlatMapMessage build(){
//        return new FlatMapMessage();
//    }
//}
