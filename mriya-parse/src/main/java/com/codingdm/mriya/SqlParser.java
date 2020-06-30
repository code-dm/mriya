package com.codingdm.mriya;

import com.codingdm.mriya.model.Column;

import java.util.List;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 6/10/2020 9:28 AM
 **/
public interface SqlParser {

    /**
     * 解析 alter 语句
     * @param sql sql
     * @return list
     */
    List<Column> parserAlterSql(String sql);

    /**
     * 解析更改表名sql， 获取老的表名
     * @param sql sql
     * @return old name
     */
    String renameTable(String sql);
}
