package com.codingdm.mriya.model;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Types;
import java.util.Objects;

/**
 * 数据库字段类
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 6/8/2020 2:06 PM
 **/
@Slf4j
@Data
@ToString
public class ColumnData {

    private int type;

    private String columnName;

    private Object columnValue;

    public ColumnData(String columnName, Object columnValue, int type){
        setColumnName(columnName);
        setColumnValue(columnValue);
        setType(type);
    }
}
