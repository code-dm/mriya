package com.codingdm.mriya.model;

import com.codingdm.mriya.common.enums.EventType;
import org.junit.Test;

public class MessageTest {

    @Test
    public void createMessage(){
        String jsonString = "{\"data\":null,\"database\":\"mriya\",\"es\":1590903517000,\"id\":1,\"isDdl\":true,\"mysqlType\":null,\"old\":null,\"pkNames\":null,\"sql\":\"/* ApplicationName=DataGrip 2020.1.4 */ CREATE TABLE `mriya`.`table_private_key`  (\\n   PRIMARY KEY (`t2`, `t1`),\\n  `t1` int(10) NOT NULL,\\n  `t2` int(10) NOT NULL\\n)\",\"sqlType\":null,\"table\":\"table_private_key\",\"ts\":1591102236018,\"type\":\"CREATE\"}";
        Message message = Message.buildMessage(jsonString);
        System.out.println(message.toString());
    }



    @Test
    public void testData(){
        String jsonString = "{\"data\":[{\"id\":\"6\",\"flow_num\":\"6\",\"order_num\":null,\"product_id\":null,\"paid_amount\":null,\"paid_method\":null," +
                "\"buy_counts\":\"0000-00-00\"," +
                "\"create_time\":\"0000-00-00 00:00:00\"}],\"database\":\"alipay\",\"es\":1590890167000,\"id\":1,\"isDdl\":false,\"mysqlType\":{\"id\":\"varchar(20)\",\"flow_num\":\"varchar(20)\",\"order_num\":\"varchar(20)\",\"product_id\":\"varchar(20)\",\"paid_amount\":\"varchar(11)\",\"paid_method\":\"int(11)\",\"buy_counts\":\"int(11)\",\"create_time\":\"datetime\"},\"old\":null,\"pkNames\":[\"id\"],\"sql\":\"\",\"sqlType\":{\"id\":12,\"flow_num\":12,\"order_num\":12,\"product_id\":12,\"paid_amount\":12,\"paid_method\":4,\"buy_counts\":4,\"create_time\":93},\"table\":\"flow\",\"ts\":1590892743870,\"type\":\"INSERT\"}";
        Message message = Message.buildMessage(jsonString);
        System.out.println(message.getData());
        System.out.println(message.getType());
        System.out.println(message.getType() == EventType.INSERT);
    }
}
