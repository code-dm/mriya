package com.codingdm.mriya.mysql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.codingdm.mriya.SqlParser;
import com.codingdm.mriya.model.Column;
import com.codingdm.mriya.mysql.parser.MysqlParser;
import com.codingdm.mriya.mysql.visitor.MysqlAlterTableVisitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 6/10/2020 9:27 AM
 **/
@Slf4j
public class MysqlSqlParserImpl implements SqlParser {


    @Override
    public List<Column> parserAlterSql(String sql) {
        if(StringUtils.isNotBlank(sql)){
            List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
            MysqlParser parser = new MysqlParser();
            MysqlAlterTableVisitor visitor = new MysqlAlterTableVisitor(parser);
            for (SQLStatement stmt : stmtList) {
                stmt.accept(visitor);
            }
            return parser.getColumns();
        }
        return null;
    }

    @Override
    public String renameTable(String sql) {
        if(StringUtils.isNotBlank(sql)){
            List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
            MysqlParser parser = new MysqlParser();
            MysqlAlterTableVisitor visitor = new MysqlAlterTableVisitor(parser);
            for (SQLStatement stmt : stmtList) {
                stmt.accept(visitor);
            }
            return Column.eraseName(parser.getOldTableName());
        }
        return null;
    }
}
