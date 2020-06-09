package com.codingdm.mriya.connection.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.SinkConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: mriya
 * @description: greenplum数据库链接
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/08 22:18
 */
@Log4j
public final class GpConnection implements SinkConnection {
    private final static String SELECT_SQL = "select 1";
    private static DruidPooledConnection pooledConnection;

    @Override
    public Connection getConnection() {
        if(pooledConnection == null || !check()){
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setUrl(NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_URL));
            druidDataSource.setUsername(NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_USERNAME));
            druidDataSource.setPassword(NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_PASSWORD));
            druidDataSource.setDefaultAutoCommit(false);
            try {
                pooledConnection = druidDataSource.getConnection();
            } catch (SQLException e) {
                log.error("greenplum db connection failed");
                e.printStackTrace();
                throw new RuntimeException("greenplum db connection failed");
            }
        }
        return pooledConnection.getConnection();
    }

    private boolean check(){
        if(pooledConnection != null){
            try {
                Connection connection = pooledConnection.getConnection();
                connection.createStatement().execute(SELECT_SQL);
                return true;
            } catch (SQLException e) {
                log.error("check greenplum connection is closed");
            }
        }
        return false;
    }

    @Override
    public void close() {
        if(pooledConnection != null){
            try {
                pooledConnection.close();
            } catch (SQLException throwables) {
                log.info("greenplum connection had closed");
            }
        }
    }
}
