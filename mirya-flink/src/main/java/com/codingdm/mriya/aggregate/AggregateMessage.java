package com.codingdm.mriya.aggregate;

import com.codingdm.mriya.model.ColumnData;
import com.codingdm.mriya.model.MergeData;
import com.codingdm.mriya.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.util.*;

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
            for (Map<String, Object> d : inMsg.getData()) {
                String pkValuesIds = inMsg.getPkValuesIds(d);
                List<ColumnData> columns = dataMapToColumnList(d, inMsg.getSqlType());
                MergeData mg = new MergeData(pkValuesIds, inMsg.getType(), columns);
                mergeData.remove(mg);
                mergeData.add(mg);
            }
            accumulator.setMergeData(mergeData);
        }
        accumulator.setData(null);
        return accumulator;
    }

    private List<ColumnData> dataMapToColumnList(Map<String, Object> data, Map<String, Integer> sqlType){
        ArrayList<ColumnData> columns = new ArrayList<>(data.keySet().size());
        for(String key : data.keySet()){
            columns.add(new ColumnData(key, data.get(key), sqlType.get(key)));
        }
        return columns;
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
