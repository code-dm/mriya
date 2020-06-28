package com.codingdm.mriya.utils;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.impl.MysqlConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/24/2020 10:27 AM
 **/
@Slf4j
public class MysqlUtilsTest {

    @Test
    public void getTableByDatabase()throws Exception {
        Connection mysqlCon = new MysqlConnection().getConnection();
        String topic = NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_TOPIC);
        List<Message> manufacturing = MysqlUtils.getTableByDatabase(topic, "manufacturing", mysqlCon);
        manufacturing.forEach(m-> System.out.println(m.getTargetTable()));
        try {
            mysqlCon.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void getTableBySource() throws Exception {
        Connection mysqlCon = new MysqlConnection().getConnection();
        String topic = NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_TOPIC);

        List<Message> manufacturing = MysqlUtils.getTableBySource(topic, mysqlCon);
        manufacturing.forEach(m-> System.out.println(m.getTargetTable()));
        try {
            mysqlCon.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void getTableByTable(){
//        Connection mysqlCon = new MysqlConnection().getConnection();
        String topic = NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_TOPIC);

        List<Message> manufacturing = MysqlUtils.getTableByTable(topic,"manufacturing","work_centers");
        manufacturing.forEach(m-> System.out.println(m.getTargetTable()));

    }

    @Test
    public void getCreateTableSql()throws Exception {
        Connection mysqlCon = new MysqlConnection().getConnection();
        String topic = NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_TOPIC);
        List<Message> manufacturing = MysqlUtils.getTableByDatabase(topic, "manufacturing", mysqlCon);
        manufacturing.forEach(m-> System.out.println(m.getTargetTable()));
        MysqlUtils.getCreateTableSql(manufacturing, mysqlCon);
        manufacturing.forEach(m-> System.out.println(m.getSql()));
        try {
            mysqlCon.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
