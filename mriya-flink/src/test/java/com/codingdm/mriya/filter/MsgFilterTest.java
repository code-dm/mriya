package com.codingdm.mriya.filter;

import com.codingdm.mriya.model.Message;
import org.junit.Test;

/**
 * @program: mriya
 * @description:
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/22 20:16
 */
public class MsgFilterTest {
    @Test
    public void testFilter(){
        Message msg = new Message();

        msg.setTopic("mriya");
        msg.setDatabase("database");
        msg.setTable("test_table");

        System.out.println(MsgFilter.filter(msg));
    }

    @Test
    public void testFilter2(){
        Message msg = new Message();

        msg.setTopic("mriya");
        msg.setDatabase("database2");
        msg.setTable("test_table");

        System.out.println(MsgFilter.filter(msg));
    }
}
