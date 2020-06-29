package com.codingdm.mriya.utils;

import com.codingdm.mriya.aggregate.AggregateMessage;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.impl.GpConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.transformer.Transformer;
import com.codingdm.mriya.transformer.impl.MysqlTransformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/28/2020 2:55 PM
 **/
@Slf4j
public class GreenPlumUtilsTest {

    @Test
    public void getCopySql() throws IOException, SQLException {
        String jsonString = "";

        Message message = Message.buildMessage(jsonString, "mes");

        // 执行copy
        Transformer transformer = new MysqlTransformer();
        List<String> columnsList = transformer.getColumnsList(message);
        if(CollectionUtils.isNotEmpty(columnsList)){
            String schemaName = NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA);
            String copySql = GreenPlumUtils.getCopySql(columnsList, schemaName, message.getTargetTable());

            System.out.println(copySql);

//            AggregateMessage aggregateMessage = new AggregateMessage();
//            Message add = aggregateMessage.add(message, null);

            byte[] bytes = GreenPlumUtils.serializeRecord(new ArrayList<>(message.getRowData()));
            InputStream in = new ByteArrayInputStream(bytes);
            Connection con = new GpConnection().getConnection();
            CopyManager copyManager = new CopyManager((BaseConnection) con);
            copyManager.copyIn(copySql, in);
            in.close();
        }
    }

}
