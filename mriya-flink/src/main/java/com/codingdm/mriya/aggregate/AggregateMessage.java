package com.codingdm.mriya.aggregate;

import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.model.ColumnData;
import com.codingdm.mriya.model.RowData;
import com.codingdm.mriya.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
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
            if(StringUtils.isNotBlank(inMsg.getSql())){
                accumulator.setSql(inMsg.getSql() + ";" +  accumulator.getSql());
            }
        }
        else if(isMerge){
            Set<RowData> rowData = accumulator.getRowData();
            if(rowData == null){
                rowData = new HashSet<>();
            }
            for (Map<String, Object> d : inMsg.getData()) {
                String pkValuesIds = inMsg.getPkValuesIds(d);
                if(pkValuesIds != null){
                    List<ColumnData> columns = dataMapToColumnList(d, inMsg.getSqlType());
                    RowData mg = new RowData(pkValuesIds, inMsg.getType(), columns);
                    rowData.remove(mg);
                    rowData.add(mg);
                }
            }
            accumulator.setRowData(rowData);
        }
        accumulator.setData(null);
        accumulator.setType(inMsg.getType());
        return accumulator;
    }

    private List<ColumnData> dataMapToColumnList(Map<String, Object> data, Map<String, Integer> sqlType){
        ArrayList<ColumnData> columns = new ArrayList<>(data.keySet().size());
        for(String key : data.keySet()){
            columns.add(new ColumnData(key, data.get(key), sqlType.get(key)));
        }
        SimpleDateFormat df = new SimpleDateFormat(CommonConstants.NOW_FORMAT);
        columns.add(new ColumnData(CommonConstants.DW_MODIFY_DATE, df.format(new Date()), Types.TIMESTAMP));
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
