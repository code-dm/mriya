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
     * @param columns column
     */
    List<Column> addColumns(List<Column> columns);

    /**
     * drop column
     * @return list
     * @param columns column
     */
    List<Column> dropColumns(List<Column> columns);

    /**
     * modify column
     * @return list
     * @param columns columns
     */
    List<Column> modifyColumns(List<Column> columns);

    /**
     * rename column
     * @return list
     * @param columns columns
     */
    List<Column> renameColumns(List<Column> columns);

}
