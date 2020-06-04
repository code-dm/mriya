package com.codingdm.mriya.config;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;

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
        properties.setProperty("serverAddr", "127.0.0.1:8848");
        properties.setProperty("namespace", "dabf5d10-b374-4d7d-b1d2-a1fad9c70b41");

        NamingService naming = NamingFactory.createNamingService(properties);

        naming.registerInstance("nacos.test.3", "127.0.0.1", 8888, "TEST1");

//        naming.registerInstance("nacos.test.3", "2.2.2.2", 9999, "DEFAULT");

//        System.out.println(naming.getAllInstances("nacos.test.3"));

//        naming.deregisterInstance("nacos.test.3", "2.2.2.2", 9999, "DEFAULT");

        System.out.println(naming.getAllInstances("nacos.test.3"));

        naming.subscribe("nacos.test.3", new ServerListener());
    }
}
