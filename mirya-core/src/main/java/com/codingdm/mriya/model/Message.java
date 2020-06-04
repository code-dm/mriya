package com.codingdm.mriya.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.codingdm.mriya.common.enums.EventType;
import com.codingdm.mriya.constant.CommonConstants;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/2/2020 5:29 PM
 **/
@Slf4j
@Data
@ToString
public class Message implements Serializable {

    private static final long serialVersionUID = -3386650678735860050L;
    private long id;
    private String database;
    private String table;
    private Set<String> pkNames;
    private Boolean isDdl;
    private EventType type;
    private Long es;
    private Long ts;
    private String sql;
    private Map<String, Integer> sqlType;
    private Map<String, String> mysqlType;
    private List<Map<String, String>> data;
    private List<Map<String, String>> old;
    private String topic;
    public String targetTable;
//    public String keyByTable;
//    private Map<String, String> dataMap;
    private Map<String, Map<String, String>> deleteData;
    private Map<String, Map<String, String>> inOrUpData;

    public List<Map<String, String>> getData() {
        if (data != null && data.size() > 0) {
            // 处理时间问题 0000-00-00  --> 统一修改为 1970-01-01 00:00:00
            if (data.toString().contains(CommonConstants.ERROR_DATE)) {
                for (Map<String, String> datum : data) {
                    for (String key : datum.keySet()) {
                        if (!StringUtils.isEmpty(datum.get(key))) {
                            // 0000-00-00 00:00:00
                            if (datum.get(key).equals(CommonConstants.ERROR_DATE_TIME)) {
                                datum.put(key, CommonConstants.CORRET_DATE_TIME);
                            }
                            // 0000-00-00
                            if (datum.get(key).equals(CommonConstants.ERROR_DATE)) {
                                datum.put(key, CommonConstants.CORRET_DATE);
                            }
                        }

                    }
                }
            }
        }
        return data;
    }

    public static Message buildMessage(String jsonString, String topic){
        if(StringUtils.isNotEmpty(jsonString)){
            Message msg = JSONObject.parseObject(jsonString, Message.class);
            msg.setTopic(topic);
            msg.setTargetTable();
//            msg.setKeyByTable();
            return msg;
        }
        return new Message();
    }

    public void setTargetTable() {
        this.targetTable = String.format(CommonConstants.TARGET_TABLE, this.getTopic(), this.getDatabase(), this.getTable());
    }

//    public void addData(List<Map<String, String>> data){
//        this.data.addAll(data);
//    }

//    public void setKeyByTable(){
//        String type = "";
//        if(typeIsDdl()){
//            type = "DDL_" + this.type;
//        }else {
//            type = "DML";
//        }
//        this.keyByTable = String.format(CommonConstants.KEY_BY_TABLE, this.getTopic(), this.getDatabase(), this.getTable(), type);
//    }

    public boolean typeIsDdl(){
        return !(this.type == EventType.INSERT
                || this.type == EventType.UPDATE
                || this.type == EventType.DELETE);

    }

    /**
     * 根据data 可以获取到该data的唯一值，可用于去重
     * @param v map
     * @return str
     */
    public String getPkValuesIds(Map<String, String> v) {
        return String.format(
                CommonConstants.PK_VALUES_IDS,
                this.getTargetTable(),
                this.getPkNames()
                        .stream()
                        .map(v::get)
                        .collect(Collectors.joining(CommonConstants.FILTER_LINE)));
    }

    public String toJsonString(){
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
