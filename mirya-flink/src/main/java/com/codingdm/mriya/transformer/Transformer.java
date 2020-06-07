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
     * 获取字段名称字符串
     * @param message
     * @return
     */
    public String getComlunsStr(Message message);

    /**
     * 获取所有字段
     * @param message
     * @return
     */
    public List<String> getColumnsList(Message message);

    /**
     * 获取删除SQL语句
     * @param message
     * @return
     */
    public String getDeleteSql(Message message);
}
