package com.codingdm.mriya.mysql.parser;


import com.codingdm.mriya.mysql.ast.MySqlParser;
import com.codingdm.mriya.mysql.ast.MySqlParserBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;


public class MySqlAntlrDdlParserListener extends MySqlParserBaseListener {


    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println("enterEveryRule");
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("exitEveryRule");
        super.exitEveryRule(ctx);
    }


    @Override
    public void enterAlterTable(MySqlParser.AlterTableContext ctx) {

        System.out.println("enterAlterTable");
        super.enterAlterTable(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void exitAlterTable(MySqlParser.AlterTableContext ctx) {

        System.out.println("exitAlterTable");

        super.exitAlterTable(ctx);
    }

    @Override
    public void enterAlterByChangeColumn(MySqlParser.AlterByChangeColumnContext ctx) {
        System.out.println("enterAlterByChangeColumn");
        super.enterAlterByChangeColumn(ctx);
    }

    @Override
    public void exitAlterByChangeColumn(MySqlParser.AlterByChangeColumnContext ctx) {

        super.exitAlterByChangeColumn(ctx);
    }

}
