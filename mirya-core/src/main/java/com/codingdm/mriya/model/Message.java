package com.codingdm.mriya.model;

import com.alibaba.fastjson.JSONObject;
import com.codingdm.mriya.common.enums.EventType;
import com.codingdm.mriya.constant.CommonConstants;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/2/2020 5:29 PM
 **/
@Slf4j
@Data
@ToString
public class Message {

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

    public static Message buildMessage(String jsonString){
        if(StringUtils.isNotEmpty(jsonString)){
            return JSONObject.parseObject(jsonString, Message.class);
        }
        return new Message();
    }
}
