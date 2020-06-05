package com.codingdm.mriya.config;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @program: mriya
 * @description: Flink服务注册
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/04 22:13
 */
public class ServerRegister {
    public static void main(String[] args) throws NacosException {


        Properties properties = new Properties();
        properties.setProperty("serverAddr", "10.168.2.224:8848");
        properties.setProperty("namespace", "public");

        NamingService naming = NamingFactory.createNamingService(properties);
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        naming.registerInstance("mriya.server", ip, 0, "MRIYA");
        while (true){
            naming.subscribe("mriya.server",new EventListener() {
                @Override
                public void onEvent(Event event) {
                    System.out.println(((NamingEvent)event).getServiceName());
                    System.out.println(((NamingEvent)event).getInstances());
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
