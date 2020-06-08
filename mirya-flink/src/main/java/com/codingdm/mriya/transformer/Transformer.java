package com.codingdm.mriya.transformer;

import com.codingdm.mriya.model.Message;

import java.util.List;

/**
 * @program: mriya
 * @description: 数据转换接口
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/07 16:13
 */
public interface Transformer {

    /**
     * 获取所有字段
     * @param message msg
     * @return list
     */
    List<String> getColumnsList(Message message);

    /**
     * 获取删除SQL语句
     * @param message msg
     * @return delete sql
     */
    String getDeleteSql(Message message);

    /**
     * 处理DDLsql语句
     * @param message msg
     * @return sql
     */
    List<String> getDdlSql(Message message);
}
