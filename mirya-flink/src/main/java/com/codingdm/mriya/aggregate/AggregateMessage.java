package com.codingdm.mriya.aggregate;

import com.codingdm.mriya.common.enums.EventType;
import com.codingdm.mriya.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/3/2020 4:03 PM
 **/
@Slf4j
public class AggregateMessage implements AggregateFunction<Message, Message, Message> {
//    private Map<String, Map<String, String>> deleteData;
//    private Map<String, Map<String, String>> inOrUpData;
//    private StringBuilder sqlBuilder;

    @Override
    public Message createAccumulator() {
//        deleteData = new HashMap<>();
//        inOrUpData = new HashMap<>();
//        sqlBuilder = new StringBuilder();
        return null;
    }

    /**
     * @param message
     * @param accumulator
     * @return
     */
    @Override
    public Message add(Message message, Message accumulator) {
        if (accumulator == null ) {
            accumulator = message;
        } else {
            if (message.typeIsDdl()) {
                accumulator.setSql(accumulator.getSql() + ";" + message.getSql());
            }
            if(message.getType() == EventType.DELETE && message.getData() != null){
                for (Map<String, String> delete : message.getData()) {
                    String pkValuesIds = message.getPkValuesIds(delete);
                    Map<String, Map<String, String>> deleteData = accumulator.getDeleteData();
                    if(deleteData == null){
                        deleteData = new HashMap<>();
                    }
                    deleteData.put(pkValuesIds, delete);
                    accumulator.setDeleteData(deleteData);
                }
            }
            boolean b = (message.getType() == EventType.INSERT
                    || message.getType() == EventType.UPDATE)
                    && message.getData() != null;
            if(b){
                for (Map<String, String> inOrUp : message.getData()) {
                    String pkValuesIds = message.getPkValuesIds(inOrUp);
                    Map<String, Map<String, String>> inOrUpData = accumulator.getDeleteData();
                    if(inOrUpData == null){
                        inOrUpData = new HashMap<>();
                    }
                    inOrUpData.put(pkValuesIds, inOrUp);
                    accumulator.setInOrUpData(inOrUpData);
                }
            }
        }
        accumulator.setData(null);
        return accumulator;
    }

    @Override
    public Message getResult(Message message) {
//        message.setDeleteData(deleteData);
//        message.setInOrUpData(inOrUpData);
//        message.setSql(sqlBuilder.toString());
        return message;
    }

    @Override
    public Message merge(Message message, Message newMessage) {
        return message;
    }

    public static AggregateMessage build() {
        return new AggregateMessage();
    }
}
