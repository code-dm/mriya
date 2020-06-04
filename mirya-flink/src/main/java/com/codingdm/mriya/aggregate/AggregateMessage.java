package com.codingdm.mriya.aggregate;

import com.codingdm.mriya.enums.EventType;
import com.codingdm.mriya.model.MergeData;
import com.codingdm.mriya.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    /**
     * @param inMsg
     * @param accumulator
     * @return
     */
    @Override
    public Message add(Message inMsg, Message accumulator) {
        if (accumulator == null) {
            accumulator = inMsg;
        }
        boolean isMerge = inMsg.getData() != null
                && !inMsg.typeIsDdl();
        if (inMsg.typeIsDdl()) {
            accumulator.setSql(accumulator.getSql() + ";" + inMsg.getSql());
        }else if(isMerge){
            Set<MergeData> mergeData = accumulator.getMergeData();
            if(mergeData == null){
                mergeData = new HashSet<>();
            }
            for (Map<String, String> d : inMsg.getData()) {
                String pkValuesIds = inMsg.getPkValuesIds(d);
                MergeData mg = new MergeData(pkValuesIds, inMsg.getType(), d);
                mergeData.remove(mg);
                mergeData.add(mg);
            }
            accumulator.setMergeData(mergeData);
        }
        accumulator.setData(null);
        return accumulator;
    }

    @Override
    public Message getResult(Message message) {

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
