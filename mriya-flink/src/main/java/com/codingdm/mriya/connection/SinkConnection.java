package com.codingdm.mriya.connection;

import java.sql.Connection;

/**
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 6/9/2020 1:20 PM
 **/
public interface SinkConnection {
    /**
     * 获取连接
     * @return con
     */
    Connection getConnection();

    /**
     * 关闭数据库连接
     */
    void close();
}
