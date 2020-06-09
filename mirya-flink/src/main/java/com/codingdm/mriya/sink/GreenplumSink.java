package com.codingdm.mriya.sink;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.SinkConnection;
import com.codingdm.mriya.connection.impl.GpConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.transformer.Transformer;
import com.codingdm.mriya.transformer.impl.MysqlTransformer;
import com.codingdm.mriya.utils.GreenPlumUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/4/2020 3:21 PM
 **/
@Slf4j
public class GreenplumSink extends RichSinkFunction<Message> {
    private SinkConnection sinkConnection;

    @Override
    public void open(Configuration parameters) throws Exception {
        if(sinkConnection == null){
            sinkConnection = new GpConnection();
            log.info("GreenplumSink GpConnection is open");
        }
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        if(sinkConnection != null){
            sinkConnection.close();
            log.info("GreenplumSink GpConnection is closed");
        }
        super.close();
    }

    @Override
    public void invoke(Message message, Context out) throws Exception {
        // 数据库连接
        Connection con = sinkConnection.getConnection();
        Statement statement = con.createStatement();
        // 处理删除数据
        Transformer transformer = new MysqlTransformer();
        List<String> deleteSql = transformer.getDeleteSql(message);
        for (String del : deleteSql) {
            statement.execute(del);
        }
        // 执行copy
        List<String> columnsList = transformer.getColumnsList(message);
        CopyManager copyManager = new CopyManager((BaseConnection) con);
        String schemaName = NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA);
        String copySql = GreenPlumUtils.getCopySql(columnsList, schemaName, message.getTargetTable());
        byte[] bytes = GreenPlumUtils.serializeRecord(new ArrayList<>(message.getRowData()));
        InputStream in = new ByteArrayInputStream(bytes);
        copyManager.copyIn(copySql, in);
        in.close();
        // 执行ddl语句


        con.commit();
    }
}
