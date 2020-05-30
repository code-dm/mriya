package com.codingdm.mriya.mysql.parser;


import com.codingdm.mriya.mysql.ast.MySqlParser;
import com.codingdm.mriya.mysql.ast.MySqlParserBaseListener;
import com.codingdm.mriya.mysql.parser.listener.AlterTableParserListener;
import lombok.extern.log4j.Log4j;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.util.ArrayList;
import java.util.List;

@Log4j
public class MySqlAntlrDdlParserListener extends MySqlParserBaseListener {

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println(ctx);
        System.out.println("enterEveryRule");

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("exitEveryRule");
    }

    @Override
    public void enterAlterTable(MySqlParser.AlterTableContext ctx) {
        System.out.println(ctx);
        System.out.println("enterAlterTable");
        String tableName = ctx.tableName().getText();
        System.out.println(tableName);
    }

    @Override
    public void exitAlterTable(MySqlParser.AlterTableContext ctx) {
        System.out.println("exitAlterTable");
    }

}
