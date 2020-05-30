package com.codingdm.mriya;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.codingdm.mriya.antlr.model.Column;

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
     * @param definition column
     */
    void dropColumns(SQLColumnDefinition definition);

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
     * rename column
     * @return list
     * @param columns columns
     */
    void renameColumns(List<Column> columns);

}
