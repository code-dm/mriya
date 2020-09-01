package com.codingdm.mriya.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.enums.EventType;
import com.codingdm.mriya.utils.TemplateUtil;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
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
    private List<Map<String, Object>> data;
    private List<Map<String, Object>> old;
    private String topic;
    public String targetTable;
    private Set<RowData> rowData;
    private List<Map<String, Object>> nodeData;

    public Message(){ }

    public Message(String topic, String database, String table){
        setDatabase(database);
        setTable(table);
        setTopic(topic);
        setTargetTable();
    }

    public Message(Message message) {
        setId(message.getId());
        setDatabase(message.getDatabase());
        setTable(message.getTable());
        setPkNames(message.getPkNames());
        setType(message.getType());
        setTs(message.getTs());
        setOld(message.getOld());
        setEs(message.getEs());
        setIsDdl(message.getIsDdl());
        setTopic(message.getTopic());

    }

    public List<Map<String, Object>> getData() {
        if (data != null && data.size() > 0) {
            // 处理时间问题 0000-00-00  --> 统一修改为 1970-01-01 00:00:00
            if (data.toString().contains(CommonConstants.ERROR_DATE)) {
                for (Map<String, Object> datum : data) {
                    for (String key : datum.keySet()) {
                        if (ObjectUtils.allNotNull(datum.get(key))) {
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
            return msg;
        }
        return new Message();
    }

    public void setTargetTable() {
        this.targetTable = formatTableName(this.getTable());
    }

    public void getOldTargetTable(String originalTableName) {
        this.targetTable = formatTableName(originalTableName);
    }

    /**
     * 获取目标的格式模板
     * @return
     */
    public String getFormatTableTemplate(){
        Map<String, String> p = new HashMap<>(2);
        p.put("topic", this.getTopic());
        p.put("database", this.getDatabase());
        String tableNameTemplate = NacosConfig.get(PropertiesConstants.MRIYA_TABLE_NAME_TEMPLATE)
                .replace("${table}", "${r'${table}'}");
        return TemplateUtil.rendering(tableNameTemplate, p);
    }

    public String formatTableName(String table) {
        Message message = new Message();
        message.setTable(table);
        return TemplateUtil.rendering(getFormatTableTemplate(), message);
    }

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
    public String getPkValuesIds(Map<String, Object> v) {
        if(v != null && this.getPkNames() != null){
            List<String> pkValues = new ArrayList<>(this.getPkNames().size());
            for (String pkName : this.getPkNames()) {
                if(v.containsKey(pkName) && v.get(pkName) != null){
                    pkValues.add(v.get(pkName).toString());
                }
            }
            return String.format(
                    CommonConstants.PK_VALUES_IDS,
                    this.getTargetTable(),
                    pkValues);
        }
        return null;
    }

    public void setTopic(String topic) {
        this.topic = topic;
        if(StringUtils.isNotEmpty(this.getTable())){
            this.setTargetTable();
        }
    }

    private static final String PK_NAME_SPLIT = "||";

    public String toJsonString(){
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
