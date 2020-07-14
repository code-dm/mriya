package com.codingdm.mriya.init;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.SinkConnection;
import com.codingdm.mriya.connection.impl.GpConnection;
import com.codingdm.mriya.connection.impl.MysqlConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.ddl.DDLTemplate;
import com.codingdm.mriya.ddl.impl.GreenplumTemplate;
import com.codingdm.mriya.filter.MsgFilter;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.utils.MysqlUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 6/23/2020 4:59 PM
 **/
@Slf4j
public class GreenplumInit implements Init{

    @Override
    public boolean initTableSchema() {
        Connection mysqlCon = new MysqlConnection().getConnection();
        System.out.println("mysqlCon");
        String topic = NacosConfig.get(PropertiesConstants.MRIYA_SOURCES_KAFKA_TOPIC);
        List<Message> tableBySource = null;
        try {
            tableBySource = MysqlUtils.getTableBySource(topic, mysqlCon).stream()
                    .filter(MsgFilter::filter).collect(Collectors.toList());

            MysqlUtils.getCreateTableSql(tableBySource, mysqlCon);
            System.out.println("end getCreateTableSql");
        } catch (Exception e) {
            log.error("get table by source exception");
            e.printStackTrace();
        }
        if(tableBySource != null){
            DDLTemplate template = new GreenplumTemplate();
            SinkConnection gpCon = new GpConnection();
            Connection connection = gpCon.getConnection();
            try {
                for (Message message : tableBySource) {
                    String sql = template.createSql(message.getSql(), message.getTargetTable(),
                            NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA),
                            NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_TYPE));
                    Statement statement = connection.createStatement();
                    System.out.println(sql);
                    statement.execute(sql);
                }
                connection.commit();
            }catch (SQLException e){
                log.error("greenplum execute create sql exception, then execute rollback");
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean initTableData() {
        return false;
    }

    public static void main(String[] args) {
        GreenplumInit init = new GreenplumInit();
        init.initTableSchema();
    }

}
