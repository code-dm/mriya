package com.codingdm.mriya.mysql.parser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLTransformVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.codingdm.mriya.mysql.ast.MySqlLexer;
import com.codingdm.mriya.mysql.ast.MySqlParser;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 1:57 PM
 **/
@Slf4j
public class TestParser {

    public static void main(String[] args) {

        String sql = "ALTER table shipping.wo_reservaion change column mto_flag mto_flag1 varchar(10) comment 'SAP侧服务和产品定义';";


        MySqlLexer lexer = new MySqlLexer(CharStreams.fromString(sql));

        MySqlParser parser = new MySqlParser(new CommonTokenStream(lexer));


        ParseTree parseTree = parser.root();

        MySqlAntlrDdlParserListener listener = new MySqlAntlrDdlParserListener();

        ParseTreeWalker.DEFAULT.walk(listener, parseTree);

        System.out.println("end");

    }
}
