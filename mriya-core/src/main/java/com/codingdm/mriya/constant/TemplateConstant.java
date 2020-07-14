package com.codingdm.mriya.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 6/10/2020 3:37 PM
 **/
@Slf4j
public class TemplateConstant {

    public static final String ALTER_DROP_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\"DROP COLUMN \"${name}\";";

    public static final String ALTER_ADD_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\" ADD COLUMN \"${name}\" ${pgColumnType};";

    public static final String ALTER_CHANGE_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\" RENAME COLUMN \"${oldName}\" TO \"${name}\";";

    public static final String ALTER_MODIFY_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\" ALTER COLUMN \"${name}\" TYPE ${pgColumnType} using \"${name}\"::${pgColumnType};";

    public static final String COLUMN_COMMENT = "COMMENT ON COLUMN \"${schema}\".\"${table}\".\"${name}\" IS '${comment}';";

    public static final String TABLE_RENAME = "ALTER TABLE \"${schema}\".\"${oldTableName}\" RENAME TO \"${table}\";";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS \"${schema}\".\"${table}\" CASCADE;";

    public static final String CREATE_TABLE_DISTRIBUTED = "CREATE TABLE IF NOT EXISTS \"${schema}\".\"${table}\"\n" +
                                                            "(\n" +
                                                            "<#list gpColumns as gpColumn>\n" +
                                                            "    \"${gpColumn.name}\" ${gpColumn.pgColumnType},\n" +
                                                            "</#list>\n" +
                                                            "dw_modify_date timestamp,\n" +
                                                            "PRIMARY KEY (${primaryKeys?join(\", \")})\n" +
                                                            " )\n" +
                                                            "<#if sourceType == 'greenplum'>\n" +
                                                            "distributed by(${primaryKeys?join(\", \")});\n" +
                                                            "<#elseif sourceType == 'postgresql'>;</#if>\n" +
                                                            "<#list gpColumns as gpColumn>\n" +
                                                            "<#if (gpColumn.comment)?default(\"\")?length gt 1 >COMMENT ON COLUMN \"${schema}\".\"${table}\".\"${gpColumn.name}\" IS '${gpColumn.comment}';</#if>\n" +
                                                            "</#list>";


    public static final String UPDATE_PRIMARY = "ALTER TABLE \"${schema}\".\"${table}\"\n" +
                                                "DROP CONSTRAINT \"${table}_pkey\";\n" +
                                                "ALTER TABLE \"${schema}\".\"${table}\"\n" +
                                                "ADD PRIMARY KEY (${primaryKeys?join(\", \")});";
}
