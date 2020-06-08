package com.codingdm.mriya.constant;

/**
 * @program: mriya
 * @description: SQL常用模板
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/08 21:14
 */
public class SqlConstants {

    public static final String SELECT_COLUMNS_SQL = "SELECT * FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA = '%s' and TABLE_NAME = '%s';";
    public static final String SELECT_TABLE_EXIST = "select count(1) as count from information_schema.tables where table_schema = '%s' and table_name = '%s';";
    public static final String TABLE_RENAME = "ALTER TABLE \"%s\".\"%s\" RENAME TO \"%s\";";
    public static final String SHOW_DATABASES = "SHOW DATABASES;";
    public static final String GET_TABLE = "SELECT table_name FROM information_schema.tables WHERE table_schema = '%s' and table_type = 'BASE TABLE';";

    public static final String DELETE_SQL = "DELETE FROM \"%s\".\"%s\" WHERE %s in (%s);";

    public static final String CLEAN_SQL = "UPDATE \"%s\".\"%s\" set %s WHERE %s in (%s);";

    public static final String REPLACE_SQL = "UPDATE \"%s\".\"%s\" set %s WHERE %s in (%s);";

    public static final String REPLACED = "\"${columnName}\" = replace( replace( replace(\"${columnName}\",'${n}', chr(${n_d})) ,'${r}', chr(${r_d})) ,'${l}', '${l_d}')";
}
