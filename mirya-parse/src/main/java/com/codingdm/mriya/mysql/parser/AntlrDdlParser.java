package com.codingdm.mriya.mysql.parser;

import com.codingdm.mriya.antlr.model.ColumnEditor;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 10:54 AM
 **/

@Data
@Log4j
public class AntlrDdlParser{

    private List<ColumnEditor> columnEditors;
}