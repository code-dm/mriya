package com.codingdm.mriya.config;

import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;

/**
 * @program: mriya
 * @description: 服务注册监听
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/04 22:15
 */
public class ServerListener implements EventListener {
    @Override
    public void onEvent(Event event) {
        System.out.println(((NamingEvent)event).getServiceName());
        System.out.println(((NamingEvent)event).getInstances());
    }
}
