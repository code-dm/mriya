package com.codingdm.mriya;

import com.codingdm.mriya.model.Column;

import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/10/2020 9:28 AM
 **/
public interface SqlParser {

    /**
     * 解析 alter 语句
     * @param sql sql
     * @return list
     */
    List<Column> parserAlterSql(String sql);
}
