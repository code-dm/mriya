package com.codingdm.mriya.filter;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.model.Message;

import java.util.List;

/**
 * @program: mriya
 * @description: 消息过滤器
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/22 19:59
 */
public class MsgFilter {
    final static String FILTER_SPILT_CHAR = "-";
    final static String ASTERISK = "*";
    final static String FILTER_FORMATER = "%s-%s-%s";

    private static String asterisk(String value, String defaultValue){
        if(value.equals(ASTERISK)){
            return ASTERISK;
        }
        return defaultValue;
    }
    
    public static boolean filter(Message msg){
        List<String> filterList = NacosConfig.getList(PropertiesConstants.MRIYA_MESSAGE_FILTER);
        for (String filter : filterList) {
            String[] split = filter.split(FILTER_SPILT_CHAR);
            String topic = msg.getTopic();
            String database = msg.getDatabase();
            String table = msg.getTable();
            topic = MsgFilter.asterisk(split[0], topic);
            database = MsgFilter.asterisk(split[1], database);
            table = MsgFilter.asterisk(split[2], table);
            if(String.format(FILTER_FORMATER, topic, database, table).equals(filter)){
                // 过滤掉
                return false;
            }

        }
        return true;
    }
}
