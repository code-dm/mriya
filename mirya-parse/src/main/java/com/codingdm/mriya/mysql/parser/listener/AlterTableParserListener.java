package com.codingdm.mriya.mysql.parser.listener;

import com.codingdm.mriya.mysql.ast.MySqlParser;
import com.codingdm.mriya.mysql.ast.MySqlParserBaseListener;
import lombok.extern.log4j.Log4j;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 10:54 AM
 **/
@Log4j
public class AlterTableParserListener extends MySqlParserBaseListener {


    @Override
    public void enterAlterTable(MySqlParser.AlterTableContext ctx) {
        System.out.println("enterAlterTable");
    }

    @Override
    public void exitAlterTable(MySqlParser.AlterTableContext ctx) {
        System.out.println("exitAlterTable");

    }


}
