package com.codingdm.mriya.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/10/2020 3:37 PM
 **/
@Slf4j
public class TemplateConstant {

    public static final String ALTER_DROP_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\"DROP COLUMN \"${name}\";";

    public static final String ALTER_ADD_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\" ADD COLUMN \"${name}\" ${pgColumnType};";

    public static final String ALTER_CHANGE_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\" RENAME COLUMN \"${oldName}\" TO \"${name}\";";

    public static final String ALTER_MODIFY_COLUMN = "ALTER TABLE \"${schema}\".\"${table}\" ALTER COLUMN \"${name}\" TYPE ${pgColumnType} using \"${name}\"::${pgColumnType};";

    public static final String COLUMN_COMMENT = "COMMENT ON COLUMN \"${schema}\".\"${table}\".\"${name}\" IS '${comment}';";

    public static final String CREATE_TABLE_DISTRIBUTED = "CREATE TABLE \"${schema}\".\"${table}\" (${fields}) distributed by(${pkNames}); ${comments}";
}
