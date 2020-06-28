package com.codingdm.mriya.connection.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.SinkConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/24/2020 9:54 AM
 **/
@Slf4j
public class MysqlConnection implements SinkConnection {
    @Override
    public Connection getConnection(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(NacosConfig.get(PropertiesConstants.MRIYA_MYSQL_DATASOURCE_URL));
        druidDataSource.setUsername(NacosConfig.get(PropertiesConstants.MRIYA_MYSQL_DATASOURCE_USERNAME));
        druidDataSource.setPassword(NacosConfig.get(PropertiesConstants.MRIYA_MYSQL_DATASOURCE_PASSWORD));
        druidDataSource.setDefaultAutoCommit(false);
        try {
            return druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("mysql db connection failed, please make sure config mriya.mysql in nacos");
        }
        return null;
    }

    @Override
    public void close() {
    }
}
