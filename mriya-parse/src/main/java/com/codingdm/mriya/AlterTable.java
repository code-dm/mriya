package com.codingdm.mriya;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;

import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/29/2020 10:54 AM
 **/
public interface AlterTable {
    /**
     * get columns by create sql
     * @return list
     * @param definitions definitions
     */
    void createTable(List<SQLColumnDefinition> definitions);

    /**
     * add column
     * @return list
     * @param definitions column
     */
    void addColumns(List<SQLColumnDefinition> definitions);

    /**
     * drop column
     * @return list
     * @param columnName column
     */
    void dropColumns(String columnName);

    /**
     * change column
     * @param definition new column
     */
    void modifyColumn(SQLColumnDefinition definition);

    /**
     * change column
     * @param definition new column
     * @param oldName oldName
     */
    void changeColumn(SQLColumnDefinition definition, String oldName);

    /**
     * set private keys
     * @param privateKey columns
     */
    void setPrivateKeys(MySqlPrimaryKey privateKey);

}
