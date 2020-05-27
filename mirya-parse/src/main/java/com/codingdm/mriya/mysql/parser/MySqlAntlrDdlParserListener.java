package com.codingdm.mriya.mysql.parser;


import com.codingdm.mriya.mysql.ast.MySqlParserBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;


public class MySqlAntlrDdlParserListener extends MySqlParserBaseListener {


    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        System.out.println("enterEveryRule");
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("exitEveryRule");
    }




}
