package com.codingdm.mriya.config;

import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

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
        System.out.println("ss");
        while (true){
            List<Instance> instances =  ((NamingEvent)event).getInstances();
            String serviceName = ((NamingEvent)event).getServiceName();
            if(instances.size() == 0) {
                System.out.println("============服务下线"+serviceName);
                System.out.println("============"+instances);
            }else {
                System.out.println("============服务上线"+serviceName);
                System.out.println("============"+instances);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
