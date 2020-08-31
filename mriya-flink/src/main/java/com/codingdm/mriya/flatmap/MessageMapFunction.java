package com.codingdm.mriya.flatmap;

import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.model.ColumnData;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.model.RowData;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 7/29/2020 1:42 PM
 **/
@Slf4j
public class MessageMapFunction implements MapFunction<Message, Message> {
    @Override
    public Message map(Message message) throws Exception {
        if(message != null){
            if (!message.typeIsDdl()) {
                Set<RowData> rowData = new HashSet<>(message.getData().size());
                for (Map<String, Object> d : message.getData()) {
                    String pkValuesIds = message.getPkValuesIds(d);
                    if(pkValuesIds != null){
                        List<ColumnData> columns = dataMapToColumnList(d, message.getSqlType());
                        RowData mg = new RowData(pkValuesIds, message.getType(), columns);
                        rowData.remove(mg);
                        rowData.add(mg);
                    }
                }
                message.setData(null);
                message.setRowData(rowData);
            }
        }
        return message;
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


    public static MessageMapFunction build() {
        return new MessageMapFunction();
    }
}
