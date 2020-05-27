package com.codingdm.mriya.antlr.model;

import com.codingdm.mriya.antlr.enums.AlterTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 1:37 PM
 **/
@Slf4j
@Data
public class ColumnEditor {

    private String name;

    private String comment;

    private String oldName;

    AlterTypeEnum alterType;

    private String type;

    private int length;

    String defaultValue;
}
