package com.codingdm.mriya.aggregate;

import com.codingdm.mriya.common.enums.EventType;
import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.model.Message;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/3/2020 4:03 PM
 **/
@Slf4j
public class AggregateMessage implements AggregateFunction<Message, Message, Message> {

    @Override
    public Message createAccumulator() {
        return null;
    }

    @Override
    public Message add(Message message, Message newMessage) {
        if(newMessage != null){
            if(newMessage.typeIsDdl()){
                message.setSql(message.getSql() + ";" + newMessage.getSql());
            }else {
                if(newMessage.getType() == EventType.DELETE && newMessage.getData() != null){
                    distintData(message, newMessage);
                }
                boolean b = (newMessage.getType() == EventType.INSERT
                        || newMessage.getType() == EventType.UPDATE)
                        && newMessage.getData() != null;
                if(b){
                    distintData(message, newMessage);
                }
                if(message.getData() == null){
                    message.setData(new ArrayList<>(newMessage.getData()));
                }else {
                    message.addData(newMessage.getData());
                }
            }
        }
        return message;
    }

    private void distintData(Message message, Message newMessage) {
        newMessage.getData().forEach(inOrUp-> {
            String pkValuesIds = newMessage.getPkValuesIds(inOrUp);
            Map<String, Map<String, String>> inOrUpdate = message.getDeleteData();
            if(inOrUpdate == null){
                inOrUpdate = new HashMap<>();
            }
            inOrUpdate.put(pkValuesIds, inOrUp);
            message.setDeleteData(inOrUpdate);
        });
    }

    @Override
    public Message getResult(Message message) {
        return message;
    }

    @Override
    public Message merge(Message message, Message newMessage) {
        return message;
    }

    public static AggregateMessage build(){
        return new AggregateMessage();
    }
}
