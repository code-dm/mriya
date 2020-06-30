/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @create 2019-08-07 15:59
 **/
package com.codingdm.mriya.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class  PgEnum {
    private Map<String, String> pgMapping;

    private PgEnum(){
        pgMapping = new HashMap<>();
        pgMapping.put("TINYINT", "int4");
        pgMapping.put("SMALLINT", "int4");
        pgMapping.put("INTEGER", "int8");
        pgMapping.put("MEDUIMINT", "int8");
        pgMapping.put("YEAR", "int8");
        pgMapping.put("BIGINT", "int8");
        pgMapping.put("INT", "int8");
        pgMapping.put("DOUBLE", "float8");
        pgMapping.put("DOUBLE PRECISION", "float8");
        pgMapping.put("FLOAT", "float8");
        pgMapping.put("REAL", "float8");
        pgMapping.put("DECIMAL", "numeric");
        pgMapping.put("NUMERIC", "numeric");
        pgMapping.put("VARCHAR", "text");
        pgMapping.put("TEXT", "text");
        pgMapping.put("CHAR", "text");
        pgMapping.put("MEDIUMTEXT", "text");
        pgMapping.put("LONGTEXT", "text");
        pgMapping.put("TINYTEXT", "text");
        pgMapping.put("JSON", "text");
        pgMapping.put("DATETIME", "timestamp");
        pgMapping.put("TIMESTAMP", "timestamp");
        pgMapping.put("DATE", "date");
        pgMapping.put("TIME", "time");
        pgMapping.put("BIT", "bit");
        pgMapping.put("BOOL", "bool");
        pgMapping.put("MEDIUMBLOB", "bytea");
        pgMapping.put("LONGBLOB", "bytea");
        pgMapping.put("TINYBLOB", "bytea");
        pgMapping.put("BLOB", "bytea");
        pgMapping.put("VARBINARY", "bytea");
        pgMapping.put("ENUM", "varchar(20)");
        pgMapping.put("BINARY", "text");
    }
    public static PgEnum getInstance(){
        return SingletonPatternHolder.pgEnum;
    }

    private static class SingletonPatternHolder {
        private static final PgEnum pgEnum = new PgEnum();
    }

    public static String getMapping(String typeName){
        if (StringUtils.isNotBlank(typeName)){
            PgEnum pgEnum = PgEnum.getInstance();
            return pgEnum.pgMapping.get(typeName.toUpperCase());
        }
        return "";
    }

}
