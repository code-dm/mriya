package com.codingdm.mriya.mysql.visitor;

import com.alibaba.druid.sql.ast.expr.SQLIntervalExpr;
import com.alibaba.druid.sql.ast.statement.SQLAlterTableAddColumn;
import com.alibaba.druid.sql.ast.statement.SQLAlterTableDropColumnItem;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.codingdm.mriya.mysql.parser.MysqlParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/29/2020 9:11 AM
 **/
@Slf4j
@Data
public class MysqlAlterTableVisitor extends MySqlASTVisitorAdapter {
    private MysqlParser parser;


    public MysqlAlterTableVisitor(MysqlParser parser){
        this.parser = parser;

    }

    @Override
    public boolean visit(SQLAlterTableDropColumnItem x) {
        System.out.println("SQLAlterTableDropColumnItem");
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableDropColumnItem x) {

    }

    @Override
    public boolean visit(SQLAlterTableAddColumn x) {
        System.out.println("SQLAlterTableAddColumn");
        if(!Objects.isNull(x) && x.getColumns().size() > 0){
            parser.addColumns(x.getColumns());
        }
        return true;
    }

    @Override
    public void endVisit(SQLAlterTableAddColumn x) {

    }

    @Override
    public boolean visit(MySqlTableIndex x) {
        return true;
    }

    @Override
    public void endVisit(MySqlTableIndex x) {

    }

    @Override
    public boolean visit(MySqlKey x) {
        return true;
    }

    @Override
    public void endVisit(MySqlKey x) {

    }

    @Override
    public boolean visit(MySqlPrimaryKey x) {

        return true;
    }

    @Override
    public void endVisit(MySqlPrimaryKey x) {

    }

    @Override
    public void endVisit(SQLIntervalExpr x) {
    }


    @Override
    public boolean visit(MySqlRenameTableStatement.Item x) {
        System.out.println("MySqlRenameTableStatement");
        System.out.println(x.getName());
        System.out.println(x.getTo());
        return true;
    }

    @Override
    public void endVisit(MySqlRenameTableStatement.Item x) {

    }

    @Override
    public boolean visit(MySqlRenameTableStatement x) {
        System.out.println("MySqlRenameTableStatement");
        return true;
    }

    @Override
    public void endVisit(MySqlRenameTableStatement x) {

    }


    @Override
    public boolean visit(MySqlAlterTableChangeColumn x) {
        System.out.println("MySqlAlterTableChangeColumn");
        if(!Objects.isNull(x.getNewColumnDefinition())){
            parser.changeColumn(x.getNewColumnDefinition(), x.getColumnName().getSimpleName());
        }
        return true;
    }

    @Override
    public boolean visit(MySqlCreateTableStatement x) {
        if(!Objects.isNull(x) && x.getTableElementList().size() > 0){
            parser.createTable(x.getTableElementList()
                    .stream()
                    .map(s->(SQLColumnDefinition)s)
                    .collect(Collectors.toList())
            );
        }
        return true;
    }

    @Override
    public boolean visit(MySqlAlterTableModifyColumn x) {
        if(!Objects.isNull(x.getNewColumnDefinition())){
            parser.modifyColumn(x.getNewColumnDefinition());
        }
        return true;
    }

    @Override
    public void endVisit(MySqlAlterTableModifyColumn x) {

    }

    @Override
    public boolean visit(MySqlAlterTableAlterColumn x) {
        System.out.println("MySqlAlterTableAlterColumn");
        return true;
    }

    @Override
    public void endVisit(MySqlAlterTableAlterColumn x) {

    }

}
