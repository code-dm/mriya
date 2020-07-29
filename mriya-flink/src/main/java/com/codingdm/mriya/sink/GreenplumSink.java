package com.codingdm.mriya.sink;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.SinkConnection;
import com.codingdm.mriya.connection.impl.GpConnection;
import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.constant.TemplateConstant;
import com.codingdm.mriya.ddl.DDLTemplate;
import com.codingdm.mriya.ddl.impl.GreenplumTemplate;
import com.codingdm.mriya.model.ColumnData;
import com.codingdm.mriya.model.GPColumn;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.model.RowData;
import com.codingdm.mriya.transformer.Transformer;
import com.codingdm.mriya.transformer.impl.MysqlTransformer;
import com.codingdm.mriya.utils.GreenPlumUtils;
import com.codingdm.mriya.utils.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.postgresql.util.PSQLException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
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
        if(CollectionUtils.isNotEmpty(deleteSql)){
            try {
                for (String del : deleteSql) {
                    statement.execute(del);
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error(deleteSql.toString());
                log.error(message.toJsonString());
                log.error(e.getMessage());
                con.rollback();
                throw new RuntimeException(e);
            }
        }
        // 执行copy
        List<String> columnsList = transformer.getColumnsList(message);
        if(columnsList != null){
            try {
                if(CollectionUtils.isNotEmpty(columnsList)){
                    String schemaName = NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA);
                    String copySql = GreenPlumUtils.getCopySql(columnsList, schemaName, message.getTargetTable());
                    CopyManager copyManager = new CopyManager((BaseConnection) con);
                    byte[] bytes = GreenPlumUtils.serializeRecord(new ArrayList<>(message.getRowData()));
                    InputStream in = new ByteArrayInputStream(bytes);
                    copyManager.copyIn(copySql, in);
                    in.close();
                }
            }catch (PSQLException e){
                System.out.println(message.toJsonString());
                System.out.println(deleteSql.toString());
                log.error(message.toJsonString());
                log.error(e.getMessage());
                e.printStackTrace();
                con.rollback();
                throw new RuntimeException(e);
            }
        }
        // 执行ddl语句
        if(StringUtils.isNotBlank(message.getSql())){
            try {
                String sql = getSql(message);
                statement.execute(sql);
            }catch (SQLException e){
                e.printStackTrace();
                log.error(message.toJsonString());
                log.error(e.getMessage());
                if(!e.getMessage().contains(CommonConstants.ALREADY_EXISTS)
                        && !e.getMessage().contains(CommonConstants.DOES_NOT_EXISTS)){
                    throw new RuntimeException(e);
                }
            }

        }
        con.commit();
    }

    private String getSql(Message message){
        DDLTemplate template = new GreenplumTemplate();
        String sql;
        switch (message.getType()){
            case ALTER:
                sql = template.alterSql(message.getSql(), message.getTargetTable(),
                        NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
                log.info(message.getTargetTable() + " alter table: " + sql);
                break;
            case CREATE:
                sql = template.createSql(message.getSql(), message.getTargetTable(),
                        NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA),
                        NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_TYPE));
                log.info(message.getTargetTable() + " create table: " + sql);
                break;
            case RENAME:
                sql = template.renameTableSql(message.getSql(), message.getTargetTable(),
                        NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA),
                        message.getFormatTableTemplate());
                log.info(message.getTargetTable() + " rename table: " + sql);
                break;
            case ERASE:
                GPColumn gpColumn = new GPColumn();
                gpColumn.setSchema(NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
                gpColumn.setTable(message.getTargetTable());
                sql = TemplateUtil.rendering(TemplateConstant.DROP_TABLE, gpColumn);
                log.info(message.getTargetTable() + " erase table: " + sql);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + message.getType());
        }

        return sql;
    }
}
