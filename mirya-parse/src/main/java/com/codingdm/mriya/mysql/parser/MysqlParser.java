package com.codingdm.mriya.mysql.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.codingdm.mriya.AlterTable;
import com.codingdm.mriya.antlr.enums.AlterTypeEnum;
import com.codingdm.mriya.antlr.model.Column;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/29/2020 11:04 AM
 **/
@Slf4j
@Data
public class MysqlParser implements AlterTable {

    private List<Column> columns;

    public MysqlParser(){

        columns = new ArrayList<>();
    }

    @Override
    public void createTable(List<SQLColumnDefinition> definitions) {
        setColumns(definitions.stream()
                .map(MysqlParser::beanConvert)
                .peek(c->c.setAlterType(AlterTypeEnum.CREATE))
                .collect(Collectors.toList())
        );
    }

    @Override
    public List<Column> addColumns(List<Column> columns) {
        return null;
    }

    @Override
    public List<Column> dropColumns(List<Column> columns) {
        return null;
    }

    @Override
    public List<Column> modifyColumns(List<Column> columns) {
        return null;
    }

    @Override
    public List<Column> renameColumns(List<Column> columns) {
        return null;
    }

    /**
     * SQLColumnDefinition -> Column
     * @param definition
     * @return
     */
    public static Column beanConvert(SQLColumnDefinition definition) {
        Column column = new Column();
        column.setName(definition.getName().getSimpleName());
        column.setType(definition.getDataType().getName());
        if(!Objects.isNull(definition.getComment())){
            column.setComment(definition.getComment().toString());
        }
        column.setPrivateKey(definition.isPrimaryKey());
        if(definition.getDataType().getArguments().size() > 0){
            column.setLength(Integer.parseInt(definition.getDataType().getArguments().get(0).toString()));
        }
        if(definition.getDataType().getArguments().size() > 1){
            column.setNumericScale(Integer.parseInt(definition.getDataType().getArguments().get(1).toString()));
        }
        return column;
    }
}
