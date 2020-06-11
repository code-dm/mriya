package com.codingdm.mriya.ddl;

/**
 * @program: mriya
 * @description: sql模板接口
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/09 21:28
 */
public interface DDLTemplate {

    /**
     * alter SQL转换
     * @param sql org sql
     * @param tableName org tableName
     * @param schema org tableName
     * @return to sql
     */
    String alterSql(String sql, String tableName, String schema);


    /**
     * create SQL转换
     * @param sql org sql
     * @param tableName tableName
     * @param schema org tableName
     * @return to sql
     */
    String createSql(String sql, String tableName, String schema);


    /**
     * rename SQL转换
     * @param sql org sql
     * @return to sql
     */
    String renameTableSql(String sql);

    /**
     * 删除表操作
     * @param sql sql
     * @return str
     */
    String eraseTable(String sql);

}
