package com.codingdm.mriya.mysql.parser.listener;

import com.codingdm.mriya.antlr.DdlParserUtils;
import com.codingdm.mriya.antlr.enums.AlterTypeEnum;
import com.codingdm.mriya.antlr.model.ColumnEditor;
import com.codingdm.mriya.mysql.ast.MySqlParser;
import com.codingdm.mriya.mysql.ast.MySqlParserBaseListener;
import com.codingdm.mriya.mysql.parser.AntlrDdlParser;
import lombok.extern.log4j.Log4j;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 10:54 AM
 **/
@Log4j
public class AlterTableParserListener extends MySqlParserBaseListener {

    private AntlrDdlParser parser;
    private final List<ParseTreeListener> listeners;
//    private ColumnDefinitionParserListener columnDefinitionListener;
    private List<ColumnEditor> columnEditors;
    private String tableName;

    public AlterTableParserListener(AntlrDdlParser parser, List<ParseTreeListener> listeners) {
        this.parser = parser;
        this.listeners = listeners;
    }


    @Override
    public void enterAlterTable(MySqlParser.AlterTableContext ctx) {
        this.tableName = ctx.tableName().fullId().getText();
        columnEditors = new ArrayList<>();
        ColumnEditor columnEditor = new ColumnEditor();
        System.out.println(DdlParserUtils.nameWithoutQuotes(this.tableName));
        columnEditor.setOldName(DdlParserUtils.nameWithoutQuotes(this.tableName));
        columnEditor.setAlterType(AlterTypeEnum.TABLE_RENAME);
        this.columnEditors.add(columnEditor);
        super.enterAlterTable(ctx);
    }

    @Override
    public void exitAlterTable(MySqlParser.AlterTableContext ctx) {
        if(parser != null){
//            listeners.remove(columnDefinitionListener);
            parser.setColumnEditors(columnEditors);
        }
        super.exitAlterTable(ctx);
    }


}
