package com.codingdm.mriya.model;

import com.codingdm.mriya.enums.AlterTypeEnum;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 1:37 PM
 **/
@Slf4j
@Data
@ToString
public class Column {

    private String name;

    private String comment;

    private String oldName;

    AlterTypeEnum alterType;

    private String type;

    private int length;

    private int numericScale;

    String defaultValue;

    boolean isPrivateKey;

    public Column(){

    }

    public Column(String name){
        this.name = name;
    }
}
